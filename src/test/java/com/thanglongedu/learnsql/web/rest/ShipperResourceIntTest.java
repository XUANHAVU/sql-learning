package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.LearnSqlApp;

import com.thanglongedu.learnsql.domain.Shipper;
import com.thanglongedu.learnsql.repository.ShipperRepository;
import com.thanglongedu.learnsql.repository.search.ShipperSearchRepository;
import com.thanglongedu.learnsql.service.ShipperService;
import com.thanglongedu.learnsql.service.dto.ShipperDTO;
import com.thanglongedu.learnsql.service.mapper.ShipperMapper;
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
 * Test class for the ShipperResource REST controller.
 *
 * @see ShipperResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnSqlApp.class)
public class ShipperResourceIntTest {

    private static final String DEFAULT_SHIPPER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private ShipperMapper shipperMapper;

    @Autowired
    private ShipperService shipperService;

    /**
     * This repository is mocked in the com.thanglongedu.learnsql.repository.search test package.
     *
     * @see com.thanglongedu.learnsql.repository.search.ShipperSearchRepositoryMockConfiguration
     */
    @Autowired
    private ShipperSearchRepository mockShipperSearchRepository;

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

    private MockMvc restShipperMockMvc;

    private Shipper shipper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipperResource shipperResource = new ShipperResource(shipperService);
        this.restShipperMockMvc = MockMvcBuilders.standaloneSetup(shipperResource)
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
    public static Shipper createEntity(EntityManager em) {
        Shipper shipper = new Shipper()
            .shipperName(DEFAULT_SHIPPER_NAME)
            .phone(DEFAULT_PHONE);
        return shipper;
    }

    @Before
    public void initTest() {
        shipper = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipper() throws Exception {
        int databaseSizeBeforeCreate = shipperRepository.findAll().size();

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);
        restShipperMockMvc.perform(post("/api/shippers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isCreated());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeCreate + 1);
        Shipper testShipper = shipperList.get(shipperList.size() - 1);
        assertThat(testShipper.getShipperName()).isEqualTo(DEFAULT_SHIPPER_NAME);
        assertThat(testShipper.getPhone()).isEqualTo(DEFAULT_PHONE);

        // Validate the Shipper in Elasticsearch
        verify(mockShipperSearchRepository, times(1)).save(testShipper);
    }

    @Test
    @Transactional
    public void createShipperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipperRepository.findAll().size();

        // Create the Shipper with an existing ID
        shipper.setId(1L);
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperMockMvc.perform(post("/api/shippers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeCreate);

        // Validate the Shipper in Elasticsearch
        verify(mockShipperSearchRepository, times(0)).save(shipper);
    }

    @Test
    @Transactional
    public void checkShipperNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperRepository.findAll().size();
        // set the field null
        shipper.setShipperName(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc.perform(post("/api/shippers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperRepository.findAll().size();
        // set the field null
        shipper.setPhone(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc.perform(post("/api/shippers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippers() throws Exception {
        // Initialize the database
        shipperRepository.saveAndFlush(shipper);

        // Get all the shipperList
        restShipperMockMvc.perform(get("/api/shippers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipper.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipperName").value(hasItem(DEFAULT_SHIPPER_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }
    
    @Test
    @Transactional
    public void getShipper() throws Exception {
        // Initialize the database
        shipperRepository.saveAndFlush(shipper);

        // Get the shipper
        restShipperMockMvc.perform(get("/api/shippers/{id}", shipper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipper.getId().intValue()))
            .andExpect(jsonPath("$.shipperName").value(DEFAULT_SHIPPER_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipper() throws Exception {
        // Get the shipper
        restShipperMockMvc.perform(get("/api/shippers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipper() throws Exception {
        // Initialize the database
        shipperRepository.saveAndFlush(shipper);

        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();

        // Update the shipper
        Shipper updatedShipper = shipperRepository.findById(shipper.getId()).get();
        // Disconnect from session so that the updates on updatedShipper are not directly saved in db
        em.detach(updatedShipper);
        updatedShipper
            .shipperName(UPDATED_SHIPPER_NAME)
            .phone(UPDATED_PHONE);
        ShipperDTO shipperDTO = shipperMapper.toDto(updatedShipper);

        restShipperMockMvc.perform(put("/api/shippers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isOk());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
        Shipper testShipper = shipperList.get(shipperList.size() - 1);
        assertThat(testShipper.getShipperName()).isEqualTo(UPDATED_SHIPPER_NAME);
        assertThat(testShipper.getPhone()).isEqualTo(UPDATED_PHONE);

        // Validate the Shipper in Elasticsearch
        verify(mockShipperSearchRepository, times(1)).save(testShipper);
    }

    @Test
    @Transactional
    public void updateNonExistingShipper() throws Exception {
        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperMockMvc.perform(put("/api/shippers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Shipper in Elasticsearch
        verify(mockShipperSearchRepository, times(0)).save(shipper);
    }

    @Test
    @Transactional
    public void deleteShipper() throws Exception {
        // Initialize the database
        shipperRepository.saveAndFlush(shipper);

        int databaseSizeBeforeDelete = shipperRepository.findAll().size();

        // Delete the shipper
        restShipperMockMvc.perform(delete("/api/shippers/{id}", shipper.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Shipper in Elasticsearch
        verify(mockShipperSearchRepository, times(1)).deleteById(shipper.getId());
    }

    @Test
    @Transactional
    public void searchShipper() throws Exception {
        // Initialize the database
        shipperRepository.saveAndFlush(shipper);
        when(mockShipperSearchRepository.search(queryStringQuery("id:" + shipper.getId())))
            .thenReturn(Collections.singletonList(shipper));
        // Search the shipper
        restShipperMockMvc.perform(get("/api/_search/shippers?query=id:" + shipper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipper.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipperName").value(hasItem(DEFAULT_SHIPPER_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shipper.class);
        Shipper shipper1 = new Shipper();
        shipper1.setId(1L);
        Shipper shipper2 = new Shipper();
        shipper2.setId(shipper1.getId());
        assertThat(shipper1).isEqualTo(shipper2);
        shipper2.setId(2L);
        assertThat(shipper1).isNotEqualTo(shipper2);
        shipper1.setId(null);
        assertThat(shipper1).isNotEqualTo(shipper2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperDTO.class);
        ShipperDTO shipperDTO1 = new ShipperDTO();
        shipperDTO1.setId(1L);
        ShipperDTO shipperDTO2 = new ShipperDTO();
        assertThat(shipperDTO1).isNotEqualTo(shipperDTO2);
        shipperDTO2.setId(shipperDTO1.getId());
        assertThat(shipperDTO1).isEqualTo(shipperDTO2);
        shipperDTO2.setId(2L);
        assertThat(shipperDTO1).isNotEqualTo(shipperDTO2);
        shipperDTO1.setId(null);
        assertThat(shipperDTO1).isNotEqualTo(shipperDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipperMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipperMapper.fromId(null)).isNull();
    }
}
