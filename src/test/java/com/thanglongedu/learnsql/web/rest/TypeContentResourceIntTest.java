package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.LearnSqlApp;

import com.thanglongedu.learnsql.domain.TypeContent;
import com.thanglongedu.learnsql.repository.TypeContentRepository;
import com.thanglongedu.learnsql.repository.search.TypeContentSearchRepository;
import com.thanglongedu.learnsql.service.TypeContentService;
import com.thanglongedu.learnsql.service.dto.TypeContentDTO;
import com.thanglongedu.learnsql.service.mapper.TypeContentMapper;
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
 * Test class for the TypeContentResource REST controller.
 *
 * @see TypeContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnSqlApp.class)
public class TypeContentResourceIntTest {

    private static final String DEFAULT_NAME_TYPE_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_NAME_TYPE_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    @Autowired
    private TypeContentRepository typeContentRepository;

    @Autowired
    private TypeContentMapper typeContentMapper;

    @Autowired
    private TypeContentService typeContentService;

    /**
     * This repository is mocked in the com.thanglongedu.learnsql.repository.search test package.
     *
     * @see com.thanglongedu.learnsql.repository.search.TypeContentSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeContentSearchRepository mockTypeContentSearchRepository;

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

    private MockMvc restTypeContentMockMvc;

    private TypeContent typeContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeContentResource typeContentResource = new TypeContentResource(typeContentService);
        this.restTypeContentMockMvc = MockMvcBuilders.standaloneSetup(typeContentResource)
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
    public static TypeContent createEntity(EntityManager em) {
        TypeContent typeContent = new TypeContent()
            .nameTypeContent(DEFAULT_NAME_TYPE_CONTENT)
            .priority(DEFAULT_PRIORITY)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return typeContent;
    }

    @Before
    public void initTest() {
        typeContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeContent() throws Exception {
        int databaseSizeBeforeCreate = typeContentRepository.findAll().size();

        // Create the TypeContent
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);
        restTypeContentMockMvc.perform(post("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeContent in the database
        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeCreate + 1);
        TypeContent testTypeContent = typeContentList.get(typeContentList.size() - 1);
        assertThat(testTypeContent.getNameTypeContent()).isEqualTo(DEFAULT_NAME_TYPE_CONTENT);
        assertThat(testTypeContent.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testTypeContent.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTypeContent.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTypeContent.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testTypeContent.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the TypeContent in Elasticsearch
        verify(mockTypeContentSearchRepository, times(1)).save(testTypeContent);
    }

    @Test
    @Transactional
    public void createTypeContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeContentRepository.findAll().size();

        // Create the TypeContent with an existing ID
        typeContent.setId(1L);
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeContentMockMvc.perform(post("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeContent in the database
        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeContent in Elasticsearch
        verify(mockTypeContentSearchRepository, times(0)).save(typeContent);
    }

    @Test
    @Transactional
    public void checkNameTypeContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContentRepository.findAll().size();
        // set the field null
        typeContent.setNameTypeContent(null);

        // Create the TypeContent, which fails.
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);

        restTypeContentMockMvc.perform(post("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isBadRequest());

        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContentRepository.findAll().size();
        // set the field null
        typeContent.setPriority(null);

        // Create the TypeContent, which fails.
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);

        restTypeContentMockMvc.perform(post("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isBadRequest());

        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContentRepository.findAll().size();
        // set the field null
        typeContent.setCreatedDate(null);

        // Create the TypeContent, which fails.
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);

        restTypeContentMockMvc.perform(post("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isBadRequest());

        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContentRepository.findAll().size();
        // set the field null
        typeContent.setCreatedBy(null);

        // Create the TypeContent, which fails.
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);

        restTypeContentMockMvc.perform(post("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isBadRequest());

        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContentRepository.findAll().size();
        // set the field null
        typeContent.setUpdatedDate(null);

        // Create the TypeContent, which fails.
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);

        restTypeContentMockMvc.perform(post("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isBadRequest());

        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContentRepository.findAll().size();
        // set the field null
        typeContent.setUpdatedBy(null);

        // Create the TypeContent, which fails.
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);

        restTypeContentMockMvc.perform(post("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isBadRequest());

        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeContents() throws Exception {
        // Initialize the database
        typeContentRepository.saveAndFlush(typeContent);

        // Get all the typeContentList
        restTypeContentMockMvc.perform(get("/api/type-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameTypeContent").value(hasItem(DEFAULT_NAME_TYPE_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getTypeContent() throws Exception {
        // Initialize the database
        typeContentRepository.saveAndFlush(typeContent);

        // Get the typeContent
        restTypeContentMockMvc.perform(get("/api/type-contents/{id}", typeContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeContent.getId().intValue()))
            .andExpect(jsonPath("$.nameTypeContent").value(DEFAULT_NAME_TYPE_CONTENT.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingTypeContent() throws Exception {
        // Get the typeContent
        restTypeContentMockMvc.perform(get("/api/type-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeContent() throws Exception {
        // Initialize the database
        typeContentRepository.saveAndFlush(typeContent);

        int databaseSizeBeforeUpdate = typeContentRepository.findAll().size();

        // Update the typeContent
        TypeContent updatedTypeContent = typeContentRepository.findById(typeContent.getId()).get();
        // Disconnect from session so that the updates on updatedTypeContent are not directly saved in db
        em.detach(updatedTypeContent);
        updatedTypeContent
            .nameTypeContent(UPDATED_NAME_TYPE_CONTENT)
            .priority(UPDATED_PRIORITY)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(updatedTypeContent);

        restTypeContentMockMvc.perform(put("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isOk());

        // Validate the TypeContent in the database
        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeUpdate);
        TypeContent testTypeContent = typeContentList.get(typeContentList.size() - 1);
        assertThat(testTypeContent.getNameTypeContent()).isEqualTo(UPDATED_NAME_TYPE_CONTENT);
        assertThat(testTypeContent.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testTypeContent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTypeContent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTypeContent.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testTypeContent.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the TypeContent in Elasticsearch
        verify(mockTypeContentSearchRepository, times(1)).save(testTypeContent);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeContent() throws Exception {
        int databaseSizeBeforeUpdate = typeContentRepository.findAll().size();

        // Create the TypeContent
        TypeContentDTO typeContentDTO = typeContentMapper.toDto(typeContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeContentMockMvc.perform(put("/api/type-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeContent in the database
        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeContent in Elasticsearch
        verify(mockTypeContentSearchRepository, times(0)).save(typeContent);
    }

    @Test
    @Transactional
    public void deleteTypeContent() throws Exception {
        // Initialize the database
        typeContentRepository.saveAndFlush(typeContent);

        int databaseSizeBeforeDelete = typeContentRepository.findAll().size();

        // Delete the typeContent
        restTypeContentMockMvc.perform(delete("/api/type-contents/{id}", typeContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeContent> typeContentList = typeContentRepository.findAll();
        assertThat(typeContentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeContent in Elasticsearch
        verify(mockTypeContentSearchRepository, times(1)).deleteById(typeContent.getId());
    }

    @Test
    @Transactional
    public void searchTypeContent() throws Exception {
        // Initialize the database
        typeContentRepository.saveAndFlush(typeContent);
        when(mockTypeContentSearchRepository.search(queryStringQuery("id:" + typeContent.getId())))
            .thenReturn(Collections.singletonList(typeContent));
        // Search the typeContent
        restTypeContentMockMvc.perform(get("/api/_search/type-contents?query=id:" + typeContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameTypeContent").value(hasItem(DEFAULT_NAME_TYPE_CONTENT)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeContent.class);
        TypeContent typeContent1 = new TypeContent();
        typeContent1.setId(1L);
        TypeContent typeContent2 = new TypeContent();
        typeContent2.setId(typeContent1.getId());
        assertThat(typeContent1).isEqualTo(typeContent2);
        typeContent2.setId(2L);
        assertThat(typeContent1).isNotEqualTo(typeContent2);
        typeContent1.setId(null);
        assertThat(typeContent1).isNotEqualTo(typeContent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeContentDTO.class);
        TypeContentDTO typeContentDTO1 = new TypeContentDTO();
        typeContentDTO1.setId(1L);
        TypeContentDTO typeContentDTO2 = new TypeContentDTO();
        assertThat(typeContentDTO1).isNotEqualTo(typeContentDTO2);
        typeContentDTO2.setId(typeContentDTO1.getId());
        assertThat(typeContentDTO1).isEqualTo(typeContentDTO2);
        typeContentDTO2.setId(2L);
        assertThat(typeContentDTO1).isNotEqualTo(typeContentDTO2);
        typeContentDTO1.setId(null);
        assertThat(typeContentDTO1).isNotEqualTo(typeContentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeContentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeContentMapper.fromId(null)).isNull();
    }
}
