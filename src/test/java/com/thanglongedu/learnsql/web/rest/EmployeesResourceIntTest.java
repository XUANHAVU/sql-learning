package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.LearnSqlApp;

import com.thanglongedu.learnsql.domain.Employees;
import com.thanglongedu.learnsql.repository.EmployeesRepository;
import com.thanglongedu.learnsql.repository.search.EmployeesSearchRepository;
import com.thanglongedu.learnsql.service.EmployeesService;
import com.thanglongedu.learnsql.service.dto.EmployeesDTO;
import com.thanglongedu.learnsql.service.mapper.EmployeesMapper;
import com.thanglongedu.learnsql.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static com.thanglongedu.learnsql.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmployeesResource REST controller.
 *
 * @see EmployeesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnSqlApp.class)
public class EmployeesResourceIntTest {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private EmployeesService employeesService;

    /**
     * This repository is mocked in the com.thanglongedu.learnsql.repository.search test package.
     *
     * @see com.thanglongedu.learnsql.repository.search.EmployeesSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmployeesSearchRepository mockEmployeesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEmployeesMockMvc;

    private Employees employees;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeesResource employeesResource = new EmployeesResource(employeesService);
        this.restEmployeesMockMvc = MockMvcBuilders.standaloneSetup(employeesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employees createEntity(EntityManager em) {
        Employees employees = new Employees()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .notes(DEFAULT_NOTES);
        return employees;
    }

    @Before
    public void initTest() {
        employees = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployees() throws Exception {
        int databaseSizeBeforeCreate = employeesRepository.findAll().size();

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);
        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isCreated());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate + 1);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployees.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployees.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployees.getNotes()).isEqualTo(DEFAULT_NOTES);

        // Validate the Employees in Elasticsearch
        verify(mockEmployeesSearchRepository, times(1)).save(testEmployees);
    }

    @Test
    @Transactional
    public void createEmployeesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeesRepository.findAll().size();

        // Create the Employees with an existing ID
        employees.setId(1L);
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Employees in Elasticsearch
        verify(mockEmployeesSearchRepository, times(0)).save(employees);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setLastName(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setFirstName(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setBirthDate(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotesIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setNotes(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList
        restEmployeesMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employees.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get the employees
        restEmployeesMockMvc.perform(get("/api/employees/{id}", employees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employees.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployees() throws Exception {
        // Get the employees
        restEmployeesMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees
        Employees updatedEmployees = employeesRepository.findById(employees.getId()).get();
        // Disconnect from session so that the updates on updatedEmployees are not directly saved in db
        em.detach(updatedEmployees);
        updatedEmployees
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .notes(UPDATED_NOTES);
        EmployeesDTO employeesDTO = employeesMapper.toDto(updatedEmployees);

        restEmployeesMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployees.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployees.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployees.getNotes()).isEqualTo(UPDATED_NOTES);

        // Validate the Employees in Elasticsearch
        verify(mockEmployeesSearchRepository, times(1)).save(testEmployees);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeesMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Employees in Elasticsearch
        verify(mockEmployeesSearchRepository, times(0)).save(employees);
    }

    @Test
    @Transactional
    public void deleteEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeDelete = employeesRepository.findAll().size();

        // Delete the employees
        restEmployeesMockMvc.perform(delete("/api/employees/{id}", employees.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Employees in Elasticsearch
        verify(mockEmployeesSearchRepository, times(1)).deleteById(employees.getId());
    }

    @Test
    @Transactional
    public void searchEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        when(mockEmployeesSearchRepository.search(queryStringQuery("id:" + employees.getId())))
            .thenReturn(Collections.singletonList(employees));
        // Search the employees
        restEmployeesMockMvc.perform(get("/api/_search/employees?query=id:" + employees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employees.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employees.class);
        Employees employees1 = new Employees();
        employees1.setId(1L);
        Employees employees2 = new Employees();
        employees2.setId(employees1.getId());
        assertThat(employees1).isEqualTo(employees2);
        employees2.setId(2L);
        assertThat(employees1).isNotEqualTo(employees2);
        employees1.setId(null);
        assertThat(employees1).isNotEqualTo(employees2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeesDTO.class);
        EmployeesDTO employeesDTO1 = new EmployeesDTO();
        employeesDTO1.setId(1L);
        EmployeesDTO employeesDTO2 = new EmployeesDTO();
        assertThat(employeesDTO1).isNotEqualTo(employeesDTO2);
        employeesDTO2.setId(employeesDTO1.getId());
        assertThat(employeesDTO1).isEqualTo(employeesDTO2);
        employeesDTO2.setId(2L);
        assertThat(employeesDTO1).isNotEqualTo(employeesDTO2);
        employeesDTO1.setId(null);
        assertThat(employeesDTO1).isNotEqualTo(employeesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeesMapper.fromId(null)).isNull();
    }
}
