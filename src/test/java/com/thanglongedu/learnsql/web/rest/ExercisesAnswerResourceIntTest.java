package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.LearnSqlApp;

import com.thanglongedu.learnsql.domain.ExercisesAnswer;
import com.thanglongedu.learnsql.repository.ExercisesAnswerRepository;
import com.thanglongedu.learnsql.repository.search.ExercisesAnswerSearchRepository;
import com.thanglongedu.learnsql.service.ExercisesAnswerService;
import com.thanglongedu.learnsql.service.dto.ExercisesAnswerDTO;
import com.thanglongedu.learnsql.service.mapper.ExercisesAnswerMapper;
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
 * Test class for the ExercisesAnswerResource REST controller.
 *
 * @see ExercisesAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnSqlApp.class)
public class ExercisesAnswerResourceIntTest {

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    @Autowired
    private ExercisesAnswerRepository exercisesAnswerRepository;

    @Autowired
    private ExercisesAnswerMapper exercisesAnswerMapper;

    @Autowired
    private ExercisesAnswerService exercisesAnswerService;

    /**
     * This repository is mocked in the com.thanglongedu.learnsql.repository.search test package.
     *
     * @see com.thanglongedu.learnsql.repository.search.ExercisesAnswerSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExercisesAnswerSearchRepository mockExercisesAnswerSearchRepository;

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

    private MockMvc restExercisesAnswerMockMvc;

    private ExercisesAnswer exercisesAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExercisesAnswerResource exercisesAnswerResource = new ExercisesAnswerResource(exercisesAnswerService);
        this.restExercisesAnswerMockMvc = MockMvcBuilders.standaloneSetup(exercisesAnswerResource)
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
    public static ExercisesAnswer createEntity(EntityManager em) {
        ExercisesAnswer exercisesAnswer = new ExercisesAnswer()
            .result(DEFAULT_RESULT)
            .isCorrect(DEFAULT_IS_CORRECT)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return exercisesAnswer;
    }

    @Before
    public void initTest() {
        exercisesAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createExercisesAnswer() throws Exception {
        int databaseSizeBeforeCreate = exercisesAnswerRepository.findAll().size();

        // Create the ExercisesAnswer
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(exercisesAnswer);
        restExercisesAnswerMockMvc.perform(post("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the ExercisesAnswer in the database
        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        ExercisesAnswer testExercisesAnswer = exercisesAnswerList.get(exercisesAnswerList.size() - 1);
        assertThat(testExercisesAnswer.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testExercisesAnswer.isIsCorrect()).isEqualTo(DEFAULT_IS_CORRECT);
        assertThat(testExercisesAnswer.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testExercisesAnswer.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExercisesAnswer.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testExercisesAnswer.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the ExercisesAnswer in Elasticsearch
        verify(mockExercisesAnswerSearchRepository, times(1)).save(testExercisesAnswer);
    }

    @Test
    @Transactional
    public void createExercisesAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exercisesAnswerRepository.findAll().size();

        // Create the ExercisesAnswer with an existing ID
        exercisesAnswer.setId(1L);
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(exercisesAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExercisesAnswerMockMvc.perform(post("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExercisesAnswer in the database
        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeCreate);

        // Validate the ExercisesAnswer in Elasticsearch
        verify(mockExercisesAnswerSearchRepository, times(0)).save(exercisesAnswer);
    }

    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesAnswerRepository.findAll().size();
        // set the field null
        exercisesAnswer.setResult(null);

        // Create the ExercisesAnswer, which fails.
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(exercisesAnswer);

        restExercisesAnswerMockMvc.perform(post("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isBadRequest());

        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesAnswerRepository.findAll().size();
        // set the field null
        exercisesAnswer.setCreatedDate(null);

        // Create the ExercisesAnswer, which fails.
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(exercisesAnswer);

        restExercisesAnswerMockMvc.perform(post("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isBadRequest());

        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesAnswerRepository.findAll().size();
        // set the field null
        exercisesAnswer.setCreatedBy(null);

        // Create the ExercisesAnswer, which fails.
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(exercisesAnswer);

        restExercisesAnswerMockMvc.perform(post("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isBadRequest());

        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesAnswerRepository.findAll().size();
        // set the field null
        exercisesAnswer.setUpdatedDate(null);

        // Create the ExercisesAnswer, which fails.
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(exercisesAnswer);

        restExercisesAnswerMockMvc.perform(post("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isBadRequest());

        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesAnswerRepository.findAll().size();
        // set the field null
        exercisesAnswer.setUpdatedBy(null);

        // Create the ExercisesAnswer, which fails.
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(exercisesAnswer);

        restExercisesAnswerMockMvc.perform(post("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isBadRequest());

        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExercisesAnswers() throws Exception {
        // Initialize the database
        exercisesAnswerRepository.saveAndFlush(exercisesAnswer);

        // Get all the exercisesAnswerList
        restExercisesAnswerMockMvc.perform(get("/api/exercises-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercisesAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getExercisesAnswer() throws Exception {
        // Initialize the database
        exercisesAnswerRepository.saveAndFlush(exercisesAnswer);

        // Get the exercisesAnswer
        restExercisesAnswerMockMvc.perform(get("/api/exercises-answers/{id}", exercisesAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exercisesAnswer.getId().intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingExercisesAnswer() throws Exception {
        // Get the exercisesAnswer
        restExercisesAnswerMockMvc.perform(get("/api/exercises-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExercisesAnswer() throws Exception {
        // Initialize the database
        exercisesAnswerRepository.saveAndFlush(exercisesAnswer);

        int databaseSizeBeforeUpdate = exercisesAnswerRepository.findAll().size();

        // Update the exercisesAnswer
        ExercisesAnswer updatedExercisesAnswer = exercisesAnswerRepository.findById(exercisesAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedExercisesAnswer are not directly saved in db
        em.detach(updatedExercisesAnswer);
        updatedExercisesAnswer
            .result(UPDATED_RESULT)
            .isCorrect(UPDATED_IS_CORRECT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(updatedExercisesAnswer);

        restExercisesAnswerMockMvc.perform(put("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the ExercisesAnswer in the database
        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeUpdate);
        ExercisesAnswer testExercisesAnswer = exercisesAnswerList.get(exercisesAnswerList.size() - 1);
        assertThat(testExercisesAnswer.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testExercisesAnswer.isIsCorrect()).isEqualTo(UPDATED_IS_CORRECT);
        assertThat(testExercisesAnswer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testExercisesAnswer.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExercisesAnswer.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testExercisesAnswer.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the ExercisesAnswer in Elasticsearch
        verify(mockExercisesAnswerSearchRepository, times(1)).save(testExercisesAnswer);
    }

    @Test
    @Transactional
    public void updateNonExistingExercisesAnswer() throws Exception {
        int databaseSizeBeforeUpdate = exercisesAnswerRepository.findAll().size();

        // Create the ExercisesAnswer
        ExercisesAnswerDTO exercisesAnswerDTO = exercisesAnswerMapper.toDto(exercisesAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExercisesAnswerMockMvc.perform(put("/api/exercises-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExercisesAnswer in the database
        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ExercisesAnswer in Elasticsearch
        verify(mockExercisesAnswerSearchRepository, times(0)).save(exercisesAnswer);
    }

    @Test
    @Transactional
    public void deleteExercisesAnswer() throws Exception {
        // Initialize the database
        exercisesAnswerRepository.saveAndFlush(exercisesAnswer);

        int databaseSizeBeforeDelete = exercisesAnswerRepository.findAll().size();

        // Delete the exercisesAnswer
        restExercisesAnswerMockMvc.perform(delete("/api/exercises-answers/{id}", exercisesAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExercisesAnswer> exercisesAnswerList = exercisesAnswerRepository.findAll();
        assertThat(exercisesAnswerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ExercisesAnswer in Elasticsearch
        verify(mockExercisesAnswerSearchRepository, times(1)).deleteById(exercisesAnswer.getId());
    }

    @Test
    @Transactional
    public void searchExercisesAnswer() throws Exception {
        // Initialize the database
        exercisesAnswerRepository.saveAndFlush(exercisesAnswer);
        when(mockExercisesAnswerSearchRepository.search(queryStringQuery("id:" + exercisesAnswer.getId())))
            .thenReturn(Collections.singletonList(exercisesAnswer));
        // Search the exercisesAnswer
        restExercisesAnswerMockMvc.perform(get("/api/_search/exercises-answers?query=id:" + exercisesAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercisesAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExercisesAnswer.class);
        ExercisesAnswer exercisesAnswer1 = new ExercisesAnswer();
        exercisesAnswer1.setId(1L);
        ExercisesAnswer exercisesAnswer2 = new ExercisesAnswer();
        exercisesAnswer2.setId(exercisesAnswer1.getId());
        assertThat(exercisesAnswer1).isEqualTo(exercisesAnswer2);
        exercisesAnswer2.setId(2L);
        assertThat(exercisesAnswer1).isNotEqualTo(exercisesAnswer2);
        exercisesAnswer1.setId(null);
        assertThat(exercisesAnswer1).isNotEqualTo(exercisesAnswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExercisesAnswerDTO.class);
        ExercisesAnswerDTO exercisesAnswerDTO1 = new ExercisesAnswerDTO();
        exercisesAnswerDTO1.setId(1L);
        ExercisesAnswerDTO exercisesAnswerDTO2 = new ExercisesAnswerDTO();
        assertThat(exercisesAnswerDTO1).isNotEqualTo(exercisesAnswerDTO2);
        exercisesAnswerDTO2.setId(exercisesAnswerDTO1.getId());
        assertThat(exercisesAnswerDTO1).isEqualTo(exercisesAnswerDTO2);
        exercisesAnswerDTO2.setId(2L);
        assertThat(exercisesAnswerDTO1).isNotEqualTo(exercisesAnswerDTO2);
        exercisesAnswerDTO1.setId(null);
        assertThat(exercisesAnswerDTO1).isNotEqualTo(exercisesAnswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(exercisesAnswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(exercisesAnswerMapper.fromId(null)).isNull();
    }
}
