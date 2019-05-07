package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.LearnSqlApp;

import com.thanglongedu.learnsql.domain.SQLQuery;
import com.thanglongedu.learnsql.repository.SQLQueryRepository;
import com.thanglongedu.learnsql.repository.search.SQLQuerySearchRepository;
import com.thanglongedu.learnsql.service.SQLQueryService;
import com.thanglongedu.learnsql.service.dto.SQLQueryDTO;
import com.thanglongedu.learnsql.service.mapper.SQLQueryMapper;
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
 * Test class for the SQLQueryResource REST controller.
 *
 * @see SQLQueryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnSqlApp.class)
public class SQLQueryResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_URL = "AAAAAAAAAA";
    private static final String UPDATED_NAME_URL = "BBBBBBBBBB";

    private static final String DEFAULT_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_QUERY = "BBBBBBBBBB";

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
    private SQLQueryRepository sQLQueryRepository;

    @Autowired
    private SQLQueryMapper sQLQueryMapper;

    @Autowired
    private SQLQueryService sQLQueryService;

    /**
     * This repository is mocked in the com.thanglongedu.learnsql.repository.search test package.
     *
     * @see com.thanglongedu.learnsql.repository.search.SQLQuerySearchRepositoryMockConfiguration
     */
    @Autowired
    private SQLQuerySearchRepository mockSQLQuerySearchRepository;

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

    private MockMvc restSQLQueryMockMvc;

    private SQLQuery sQLQuery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SQLQueryResource sQLQueryResource = new SQLQueryResource(sQLQueryService);
        this.restSQLQueryMockMvc = MockMvcBuilders.standaloneSetup(sQLQueryResource)
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
    public static SQLQuery createEntity(EntityManager em) {
        SQLQuery sQLQuery = new SQLQuery()
            .title(DEFAULT_TITLE)
            .nameUrl(DEFAULT_NAME_URL)
            .query(DEFAULT_QUERY)
            .description(DEFAULT_DESCRIPTION)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return sQLQuery;
    }

    @Before
    public void initTest() {
        sQLQuery = createEntity(em);
    }

    @Test
    @Transactional
    public void createSQLQuery() throws Exception {
        int databaseSizeBeforeCreate = sQLQueryRepository.findAll().size();

        // Create the SQLQuery
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);
        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isCreated());

        // Validate the SQLQuery in the database
        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeCreate + 1);
        SQLQuery testSQLQuery = sQLQueryList.get(sQLQueryList.size() - 1);
        assertThat(testSQLQuery.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSQLQuery.getNameUrl()).isEqualTo(DEFAULT_NAME_URL);
        assertThat(testSQLQuery.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testSQLQuery.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSQLQuery.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSQLQuery.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSQLQuery.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSQLQuery.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the SQLQuery in Elasticsearch
        verify(mockSQLQuerySearchRepository, times(1)).save(testSQLQuery);
    }

    @Test
    @Transactional
    public void createSQLQueryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sQLQueryRepository.findAll().size();

        // Create the SQLQuery with an existing ID
        sQLQuery.setId(1L);
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SQLQuery in the database
        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeCreate);

        // Validate the SQLQuery in Elasticsearch
        verify(mockSQLQuerySearchRepository, times(0)).save(sQLQuery);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sQLQueryRepository.findAll().size();
        // set the field null
        sQLQuery.setTitle(null);

        // Create the SQLQuery, which fails.
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = sQLQueryRepository.findAll().size();
        // set the field null
        sQLQuery.setNameUrl(null);

        // Create the SQLQuery, which fails.
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQueryIsRequired() throws Exception {
        int databaseSizeBeforeTest = sQLQueryRepository.findAll().size();
        // set the field null
        sQLQuery.setQuery(null);

        // Create the SQLQuery, which fails.
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sQLQueryRepository.findAll().size();
        // set the field null
        sQLQuery.setCreatedDate(null);

        // Create the SQLQuery, which fails.
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = sQLQueryRepository.findAll().size();
        // set the field null
        sQLQuery.setCreatedBy(null);

        // Create the SQLQuery, which fails.
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sQLQueryRepository.findAll().size();
        // set the field null
        sQLQuery.setUpdatedDate(null);

        // Create the SQLQuery, which fails.
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = sQLQueryRepository.findAll().size();
        // set the field null
        sQLQuery.setUpdatedBy(null);

        // Create the SQLQuery, which fails.
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        restSQLQueryMockMvc.perform(post("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSQLQueries() throws Exception {
        // Initialize the database
        sQLQueryRepository.saveAndFlush(sQLQuery);

        // Get all the sQLQueryList
        restSQLQueryMockMvc.perform(get("/api/sql-queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sQLQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].nameUrl").value(hasItem(DEFAULT_NAME_URL.toString())))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getSQLQuery() throws Exception {
        // Initialize the database
        sQLQueryRepository.saveAndFlush(sQLQuery);

        // Get the sQLQuery
        restSQLQueryMockMvc.perform(get("/api/sql-queries/{id}", sQLQuery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sQLQuery.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.nameUrl").value(DEFAULT_NAME_URL.toString()))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingSQLQuery() throws Exception {
        // Get the sQLQuery
        restSQLQueryMockMvc.perform(get("/api/sql-queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSQLQuery() throws Exception {
        // Initialize the database
        sQLQueryRepository.saveAndFlush(sQLQuery);

        int databaseSizeBeforeUpdate = sQLQueryRepository.findAll().size();

        // Update the sQLQuery
        SQLQuery updatedSQLQuery = sQLQueryRepository.findById(sQLQuery.getId()).get();
        // Disconnect from session so that the updates on updatedSQLQuery are not directly saved in db
        em.detach(updatedSQLQuery);
        updatedSQLQuery
            .title(UPDATED_TITLE)
            .nameUrl(UPDATED_NAME_URL)
            .query(UPDATED_QUERY)
            .description(UPDATED_DESCRIPTION)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(updatedSQLQuery);

        restSQLQueryMockMvc.perform(put("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isOk());

        // Validate the SQLQuery in the database
        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeUpdate);
        SQLQuery testSQLQuery = sQLQueryList.get(sQLQueryList.size() - 1);
        assertThat(testSQLQuery.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSQLQuery.getNameUrl()).isEqualTo(UPDATED_NAME_URL);
        assertThat(testSQLQuery.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testSQLQuery.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSQLQuery.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSQLQuery.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSQLQuery.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSQLQuery.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the SQLQuery in Elasticsearch
        verify(mockSQLQuerySearchRepository, times(1)).save(testSQLQuery);
    }

    @Test
    @Transactional
    public void updateNonExistingSQLQuery() throws Exception {
        int databaseSizeBeforeUpdate = sQLQueryRepository.findAll().size();

        // Create the SQLQuery
        SQLQueryDTO sQLQueryDTO = sQLQueryMapper.toDto(sQLQuery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSQLQueryMockMvc.perform(put("/api/sql-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sQLQueryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SQLQuery in the database
        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SQLQuery in Elasticsearch
        verify(mockSQLQuerySearchRepository, times(0)).save(sQLQuery);
    }

    @Test
    @Transactional
    public void deleteSQLQuery() throws Exception {
        // Initialize the database
        sQLQueryRepository.saveAndFlush(sQLQuery);

        int databaseSizeBeforeDelete = sQLQueryRepository.findAll().size();

        // Delete the sQLQuery
        restSQLQueryMockMvc.perform(delete("/api/sql-queries/{id}", sQLQuery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SQLQuery> sQLQueryList = sQLQueryRepository.findAll();
        assertThat(sQLQueryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SQLQuery in Elasticsearch
        verify(mockSQLQuerySearchRepository, times(1)).deleteById(sQLQuery.getId());
    }

    @Test
    @Transactional
    public void searchSQLQuery() throws Exception {
        // Initialize the database
        sQLQueryRepository.saveAndFlush(sQLQuery);
        when(mockSQLQuerySearchRepository.search(queryStringQuery("id:" + sQLQuery.getId())))
            .thenReturn(Collections.singletonList(sQLQuery));
        // Search the sQLQuery
        restSQLQueryMockMvc.perform(get("/api/_search/sql-queries?query=id:" + sQLQuery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sQLQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].nameUrl").value(hasItem(DEFAULT_NAME_URL)))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SQLQuery.class);
        SQLQuery sQLQuery1 = new SQLQuery();
        sQLQuery1.setId(1L);
        SQLQuery sQLQuery2 = new SQLQuery();
        sQLQuery2.setId(sQLQuery1.getId());
        assertThat(sQLQuery1).isEqualTo(sQLQuery2);
        sQLQuery2.setId(2L);
        assertThat(sQLQuery1).isNotEqualTo(sQLQuery2);
        sQLQuery1.setId(null);
        assertThat(sQLQuery1).isNotEqualTo(sQLQuery2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SQLQueryDTO.class);
        SQLQueryDTO sQLQueryDTO1 = new SQLQueryDTO();
        sQLQueryDTO1.setId(1L);
        SQLQueryDTO sQLQueryDTO2 = new SQLQueryDTO();
        assertThat(sQLQueryDTO1).isNotEqualTo(sQLQueryDTO2);
        sQLQueryDTO2.setId(sQLQueryDTO1.getId());
        assertThat(sQLQueryDTO1).isEqualTo(sQLQueryDTO2);
        sQLQueryDTO2.setId(2L);
        assertThat(sQLQueryDTO1).isNotEqualTo(sQLQueryDTO2);
        sQLQueryDTO1.setId(null);
        assertThat(sQLQueryDTO1).isNotEqualTo(sQLQueryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sQLQueryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sQLQueryMapper.fromId(null)).isNull();
    }
}
