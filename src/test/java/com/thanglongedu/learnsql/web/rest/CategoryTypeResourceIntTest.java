package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.LearnSqlApp;

import com.thanglongedu.learnsql.domain.CategoryType;
import com.thanglongedu.learnsql.repository.CategoryTypeRepository;
import com.thanglongedu.learnsql.repository.search.CategoryTypeSearchRepository;
import com.thanglongedu.learnsql.service.CategoryTypeService;
import com.thanglongedu.learnsql.service.dto.CategoryTypeDTO;
import com.thanglongedu.learnsql.service.mapper.CategoryTypeMapper;
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
 * Test class for the CategoryTypeResource REST controller.
 *
 * @see CategoryTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnSqlApp.class)
public class CategoryTypeResourceIntTest {

    private static final String DEFAULT_NAME_CATEGORY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_CATEGORY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    @Autowired
    private CategoryTypeMapper categoryTypeMapper;

    @Autowired
    private CategoryTypeService categoryTypeService;

    /**
     * This repository is mocked in the com.thanglongedu.learnsql.repository.search test package.
     *
     * @see com.thanglongedu.learnsql.repository.search.CategoryTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategoryTypeSearchRepository mockCategoryTypeSearchRepository;

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

    private MockMvc restCategoryTypeMockMvc;

    private CategoryType categoryType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoryTypeResource categoryTypeResource = new CategoryTypeResource(categoryTypeService);
        this.restCategoryTypeMockMvc = MockMvcBuilders.standaloneSetup(categoryTypeResource)
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
    public static CategoryType createEntity(EntityManager em) {
        CategoryType categoryType = new CategoryType()
            .nameCategoryType(DEFAULT_NAME_CATEGORY_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return categoryType;
    }

    @Before
    public void initTest() {
        categoryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryType() throws Exception {
        int databaseSizeBeforeCreate = categoryTypeRepository.findAll().size();

        // Create the CategoryType
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);
        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryType testCategoryType = categoryTypeList.get(categoryTypeList.size() - 1);
        assertThat(testCategoryType.getNameCategoryType()).isEqualTo(DEFAULT_NAME_CATEGORY_TYPE);
        assertThat(testCategoryType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategoryType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCategoryType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCategoryType.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testCategoryType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the CategoryType in Elasticsearch
        verify(mockCategoryTypeSearchRepository, times(1)).save(testCategoryType);
    }

    @Test
    @Transactional
    public void createCategoryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryTypeRepository.findAll().size();

        // Create the CategoryType with an existing ID
        categoryType.setId(1L);
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the CategoryType in Elasticsearch
        verify(mockCategoryTypeSearchRepository, times(0)).save(categoryType);
    }

    @Test
    @Transactional
    public void checkNameCategoryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTypeRepository.findAll().size();
        // set the field null
        categoryType.setNameCategoryType(null);

        // Create the CategoryType, which fails.
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTypeRepository.findAll().size();
        // set the field null
        categoryType.setCreatedDate(null);

        // Create the CategoryType, which fails.
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTypeRepository.findAll().size();
        // set the field null
        categoryType.setCreatedBy(null);

        // Create the CategoryType, which fails.
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTypeRepository.findAll().size();
        // set the field null
        categoryType.setUpdatedDate(null);

        // Create the CategoryType, which fails.
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryTypeRepository.findAll().size();
        // set the field null
        categoryType.setUpdatedBy(null);

        // Create the CategoryType, which fails.
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        restCategoryTypeMockMvc.perform(post("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryTypes() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList
        restCategoryTypeMockMvc.perform(get("/api/category-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameCategoryType").value(hasItem(DEFAULT_NAME_CATEGORY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get the categoryType
        restCategoryTypeMockMvc.perform(get("/api/category-types/{id}", categoryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoryType.getId().intValue()))
            .andExpect(jsonPath("$.nameCategoryType").value(DEFAULT_NAME_CATEGORY_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingCategoryType() throws Exception {
        // Get the categoryType
        restCategoryTypeMockMvc.perform(get("/api/category-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();

        // Update the categoryType
        CategoryType updatedCategoryType = categoryTypeRepository.findById(categoryType.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryType are not directly saved in db
        em.detach(updatedCategoryType);
        updatedCategoryType
            .nameCategoryType(UPDATED_NAME_CATEGORY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(updatedCategoryType);

        restCategoryTypeMockMvc.perform(put("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
        CategoryType testCategoryType = categoryTypeList.get(categoryTypeList.size() - 1);
        assertThat(testCategoryType.getNameCategoryType()).isEqualTo(UPDATED_NAME_CATEGORY_TYPE);
        assertThat(testCategoryType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategoryType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCategoryType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCategoryType.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCategoryType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the CategoryType in Elasticsearch
        verify(mockCategoryTypeSearchRepository, times(1)).save(testCategoryType);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();

        // Create the CategoryType
        CategoryTypeDTO categoryTypeDTO = categoryTypeMapper.toDto(categoryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryTypeMockMvc.perform(put("/api/category-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CategoryType in Elasticsearch
        verify(mockCategoryTypeSearchRepository, times(0)).save(categoryType);
    }

    @Test
    @Transactional
    public void deleteCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        int databaseSizeBeforeDelete = categoryTypeRepository.findAll().size();

        // Delete the categoryType
        restCategoryTypeMockMvc.perform(delete("/api/category-types/{id}", categoryType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CategoryType in Elasticsearch
        verify(mockCategoryTypeSearchRepository, times(1)).deleteById(categoryType.getId());
    }

    @Test
    @Transactional
    public void searchCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);
        when(mockCategoryTypeSearchRepository.search(queryStringQuery("id:" + categoryType.getId())))
            .thenReturn(Collections.singletonList(categoryType));
        // Search the categoryType
        restCategoryTypeMockMvc.perform(get("/api/_search/category-types?query=id:" + categoryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameCategoryType").value(hasItem(DEFAULT_NAME_CATEGORY_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryType.class);
        CategoryType categoryType1 = new CategoryType();
        categoryType1.setId(1L);
        CategoryType categoryType2 = new CategoryType();
        categoryType2.setId(categoryType1.getId());
        assertThat(categoryType1).isEqualTo(categoryType2);
        categoryType2.setId(2L);
        assertThat(categoryType1).isNotEqualTo(categoryType2);
        categoryType1.setId(null);
        assertThat(categoryType1).isNotEqualTo(categoryType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryTypeDTO.class);
        CategoryTypeDTO categoryTypeDTO1 = new CategoryTypeDTO();
        categoryTypeDTO1.setId(1L);
        CategoryTypeDTO categoryTypeDTO2 = new CategoryTypeDTO();
        assertThat(categoryTypeDTO1).isNotEqualTo(categoryTypeDTO2);
        categoryTypeDTO2.setId(categoryTypeDTO1.getId());
        assertThat(categoryTypeDTO1).isEqualTo(categoryTypeDTO2);
        categoryTypeDTO2.setId(2L);
        assertThat(categoryTypeDTO1).isNotEqualTo(categoryTypeDTO2);
        categoryTypeDTO1.setId(null);
        assertThat(categoryTypeDTO1).isNotEqualTo(categoryTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categoryTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categoryTypeMapper.fromId(null)).isNull();
    }
}
