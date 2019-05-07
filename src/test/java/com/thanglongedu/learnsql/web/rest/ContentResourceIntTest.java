package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.LearnSqlApp;

import com.thanglongedu.learnsql.domain.Content;
import com.thanglongedu.learnsql.repository.ContentRepository;
import com.thanglongedu.learnsql.repository.search.ContentSearchRepository;
import com.thanglongedu.learnsql.service.ContentService;
import com.thanglongedu.learnsql.service.dto.ContentDTO;
import com.thanglongedu.learnsql.service.mapper.ContentMapper;
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
 * Test class for the ContentResource REST controller.
 *
 * @see ContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnSqlApp.class)
public class ContentResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private ContentService contentService;

    /**
     * This repository is mocked in the com.thanglongedu.learnsql.repository.search test package.
     *
     * @see com.thanglongedu.learnsql.repository.search.ContentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContentSearchRepository mockContentSearchRepository;

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

    private MockMvc restContentMockMvc;

    private Content content;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentResource contentResource = new ContentResource(contentService);
        this.restContentMockMvc = MockMvcBuilders.standaloneSetup(contentResource)
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
    public static Content createEntity(EntityManager em) {
        Content content = new Content()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return content;
    }

    @Before
    public void initTest() {
        content = createEntity(em);
    }

    @Test
    @Transactional
    public void createContent() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);
        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isCreated());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate + 1);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testContent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testContent.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testContent.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testContent.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testContent.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(1)).save(testContent);
    }

    @Test
    @Transactional
    public void createContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content with an existing ID
        content.setId(1L);
        ContentDTO contentDTO = contentMapper.toDto(content);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(0)).save(content);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setTitle(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setContent(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setCreatedDate(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setCreatedBy(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setUpdatedDate(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setUpdatedBy(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContents() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get all the contentList
        restContentMockMvc.perform(get("/api/contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(content.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingContent() throws Exception {
        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content
        Content updatedContent = contentRepository.findById(content.getId()).get();
        // Disconnect from session so that the updates on updatedContent are not directly saved in db
        em.detach(updatedContent);
        updatedContent
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        ContentDTO contentDTO = contentMapper.toDto(updatedContent);

        restContentMockMvc.perform(put("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testContent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testContent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testContent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testContent.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testContent.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(1)).save(testContent);
    }

    @Test
    @Transactional
    public void updateNonExistingContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentMockMvc.perform(put("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(0)).save(content);
    }

    @Test
    @Transactional
    public void deleteContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        int databaseSizeBeforeDelete = contentRepository.findAll().size();

        // Delete the content
        restContentMockMvc.perform(delete("/api/contents/{id}", content.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Content in Elasticsearch
        verify(mockContentSearchRepository, times(1)).deleteById(content.getId());
    }

    @Test
    @Transactional
    public void searchContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);
        when(mockContentSearchRepository.search(queryStringQuery("id:" + content.getId())))
            .thenReturn(Collections.singletonList(content));
        // Search the content
        restContentMockMvc.perform(get("/api/_search/contents?query=id:" + content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Content.class);
        Content content1 = new Content();
        content1.setId(1L);
        Content content2 = new Content();
        content2.setId(content1.getId());
        assertThat(content1).isEqualTo(content2);
        content2.setId(2L);
        assertThat(content1).isNotEqualTo(content2);
        content1.setId(null);
        assertThat(content1).isNotEqualTo(content2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentDTO.class);
        ContentDTO contentDTO1 = new ContentDTO();
        contentDTO1.setId(1L);
        ContentDTO contentDTO2 = new ContentDTO();
        assertThat(contentDTO1).isNotEqualTo(contentDTO2);
        contentDTO2.setId(contentDTO1.getId());
        assertThat(contentDTO1).isEqualTo(contentDTO2);
        contentDTO2.setId(2L);
        assertThat(contentDTO1).isNotEqualTo(contentDTO2);
        contentDTO1.setId(null);
        assertThat(contentDTO1).isNotEqualTo(contentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contentMapper.fromId(null)).isNull();
    }
}
