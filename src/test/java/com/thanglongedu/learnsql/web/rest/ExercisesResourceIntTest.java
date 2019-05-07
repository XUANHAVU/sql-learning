package com.thanglongedu.learnsql.web.rest;

import com.thanglongedu.learnsql.LearnSqlApp;

import com.thanglongedu.learnsql.domain.Exercises;
import com.thanglongedu.learnsql.repository.ExercisesRepository;
import com.thanglongedu.learnsql.repository.search.ExercisesSearchRepository;
import com.thanglongedu.learnsql.service.ExercisesService;
import com.thanglongedu.learnsql.service.dto.ExercisesDTO;
import com.thanglongedu.learnsql.service.mapper.ExercisesMapper;
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
 * Test class for the ExercisesResource REST controller.
 *
 * @see ExercisesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnSqlApp.class)
public class ExercisesResourceIntTest {

    private static final String DEFAULT_NAME_EXERCISES = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EXERCISES = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final Integer DEFAULT_TIME_ON_MINUTES = 1;
    private static final Integer UPDATED_TIME_ON_MINUTES = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    @Autowired
    private ExercisesRepository exercisesRepository;

    @Autowired
    private ExercisesMapper exercisesMapper;

    @Autowired
    private ExercisesService exercisesService;

    /**
     * This repository is mocked in the com.thanglongedu.learnsql.repository.search test package.
     *
     * @see com.thanglongedu.learnsql.repository.search.ExercisesSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExercisesSearchRepository mockExercisesSearchRepository;

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

    private MockMvc restExercisesMockMvc;

    private Exercises exercises;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExercisesResource exercisesResource = new ExercisesResource(exercisesService);
        this.restExercisesMockMvc = MockMvcBuilders.standaloneSetup(exercisesResource)
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
    public static Exercises createEntity(EntityManager em) {
        Exercises exercises = new Exercises()
            .nameExercises(DEFAULT_NAME_EXERCISES)
            .question(DEFAULT_QUESTION)
            .score(DEFAULT_SCORE)
            .timeOnMinutes(DEFAULT_TIME_ON_MINUTES)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return exercises;
    }

    @Before
    public void initTest() {
        exercises = createEntity(em);
    }

    @Test
    @Transactional
    public void createExercises() throws Exception {
        int databaseSizeBeforeCreate = exercisesRepository.findAll().size();

        // Create the Exercises
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);
        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isCreated());

        // Validate the Exercises in the database
        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeCreate + 1);
        Exercises testExercises = exercisesList.get(exercisesList.size() - 1);
        assertThat(testExercises.getNameExercises()).isEqualTo(DEFAULT_NAME_EXERCISES);
        assertThat(testExercises.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testExercises.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testExercises.getTimeOnMinutes()).isEqualTo(DEFAULT_TIME_ON_MINUTES);
        assertThat(testExercises.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testExercises.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExercises.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testExercises.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the Exercises in Elasticsearch
        verify(mockExercisesSearchRepository, times(1)).save(testExercises);
    }

    @Test
    @Transactional
    public void createExercisesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exercisesRepository.findAll().size();

        // Create the Exercises with an existing ID
        exercises.setId(1L);
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exercises in the database
        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Exercises in Elasticsearch
        verify(mockExercisesSearchRepository, times(0)).save(exercises);
    }

    @Test
    @Transactional
    public void checkNameExercisesIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesRepository.findAll().size();
        // set the field null
        exercises.setNameExercises(null);

        // Create the Exercises, which fails.
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesRepository.findAll().size();
        // set the field null
        exercises.setQuestion(null);

        // Create the Exercises, which fails.
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesRepository.findAll().size();
        // set the field null
        exercises.setScore(null);

        // Create the Exercises, which fails.
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeOnMinutesIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesRepository.findAll().size();
        // set the field null
        exercises.setTimeOnMinutes(null);

        // Create the Exercises, which fails.
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesRepository.findAll().size();
        // set the field null
        exercises.setCreatedDate(null);

        // Create the Exercises, which fails.
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesRepository.findAll().size();
        // set the field null
        exercises.setCreatedBy(null);

        // Create the Exercises, which fails.
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesRepository.findAll().size();
        // set the field null
        exercises.setUpdatedDate(null);

        // Create the Exercises, which fails.
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = exercisesRepository.findAll().size();
        // set the field null
        exercises.setUpdatedBy(null);

        // Create the Exercises, which fails.
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        restExercisesMockMvc.perform(post("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExercises() throws Exception {
        // Initialize the database
        exercisesRepository.saveAndFlush(exercises);

        // Get all the exercisesList
        restExercisesMockMvc.perform(get("/api/exercises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercises.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameExercises").value(hasItem(DEFAULT_NAME_EXERCISES.toString())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].timeOnMinutes").value(hasItem(DEFAULT_TIME_ON_MINUTES)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getExercises() throws Exception {
        // Initialize the database
        exercisesRepository.saveAndFlush(exercises);

        // Get the exercises
        restExercisesMockMvc.perform(get("/api/exercises/{id}", exercises.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exercises.getId().intValue()))
            .andExpect(jsonPath("$.nameExercises").value(DEFAULT_NAME_EXERCISES.toString()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.timeOnMinutes").value(DEFAULT_TIME_ON_MINUTES))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingExercises() throws Exception {
        // Get the exercises
        restExercisesMockMvc.perform(get("/api/exercises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExercises() throws Exception {
        // Initialize the database
        exercisesRepository.saveAndFlush(exercises);

        int databaseSizeBeforeUpdate = exercisesRepository.findAll().size();

        // Update the exercises
        Exercises updatedExercises = exercisesRepository.findById(exercises.getId()).get();
        // Disconnect from session so that the updates on updatedExercises are not directly saved in db
        em.detach(updatedExercises);
        updatedExercises
            .nameExercises(UPDATED_NAME_EXERCISES)
            .question(UPDATED_QUESTION)
            .score(UPDATED_SCORE)
            .timeOnMinutes(UPDATED_TIME_ON_MINUTES)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(updatedExercises);

        restExercisesMockMvc.perform(put("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isOk());

        // Validate the Exercises in the database
        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeUpdate);
        Exercises testExercises = exercisesList.get(exercisesList.size() - 1);
        assertThat(testExercises.getNameExercises()).isEqualTo(UPDATED_NAME_EXERCISES);
        assertThat(testExercises.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testExercises.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExercises.getTimeOnMinutes()).isEqualTo(UPDATED_TIME_ON_MINUTES);
        assertThat(testExercises.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testExercises.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExercises.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testExercises.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the Exercises in Elasticsearch
        verify(mockExercisesSearchRepository, times(1)).save(testExercises);
    }

    @Test
    @Transactional
    public void updateNonExistingExercises() throws Exception {
        int databaseSizeBeforeUpdate = exercisesRepository.findAll().size();

        // Create the Exercises
        ExercisesDTO exercisesDTO = exercisesMapper.toDto(exercises);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExercisesMockMvc.perform(put("/api/exercises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exercisesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exercises in the database
        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Exercises in Elasticsearch
        verify(mockExercisesSearchRepository, times(0)).save(exercises);
    }

    @Test
    @Transactional
    public void deleteExercises() throws Exception {
        // Initialize the database
        exercisesRepository.saveAndFlush(exercises);

        int databaseSizeBeforeDelete = exercisesRepository.findAll().size();

        // Delete the exercises
        restExercisesMockMvc.perform(delete("/api/exercises/{id}", exercises.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Exercises> exercisesList = exercisesRepository.findAll();
        assertThat(exercisesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Exercises in Elasticsearch
        verify(mockExercisesSearchRepository, times(1)).deleteById(exercises.getId());
    }

    @Test
    @Transactional
    public void searchExercises() throws Exception {
        // Initialize the database
        exercisesRepository.saveAndFlush(exercises);
        when(mockExercisesSearchRepository.search(queryStringQuery("id:" + exercises.getId())))
            .thenReturn(Collections.singletonList(exercises));
        // Search the exercises
        restExercisesMockMvc.perform(get("/api/_search/exercises?query=id:" + exercises.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercises.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameExercises").value(hasItem(DEFAULT_NAME_EXERCISES)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].timeOnMinutes").value(hasItem(DEFAULT_TIME_ON_MINUTES)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exercises.class);
        Exercises exercises1 = new Exercises();
        exercises1.setId(1L);
        Exercises exercises2 = new Exercises();
        exercises2.setId(exercises1.getId());
        assertThat(exercises1).isEqualTo(exercises2);
        exercises2.setId(2L);
        assertThat(exercises1).isNotEqualTo(exercises2);
        exercises1.setId(null);
        assertThat(exercises1).isNotEqualTo(exercises2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExercisesDTO.class);
        ExercisesDTO exercisesDTO1 = new ExercisesDTO();
        exercisesDTO1.setId(1L);
        ExercisesDTO exercisesDTO2 = new ExercisesDTO();
        assertThat(exercisesDTO1).isNotEqualTo(exercisesDTO2);
        exercisesDTO2.setId(exercisesDTO1.getId());
        assertThat(exercisesDTO1).isEqualTo(exercisesDTO2);
        exercisesDTO2.setId(2L);
        assertThat(exercisesDTO1).isNotEqualTo(exercisesDTO2);
        exercisesDTO1.setId(null);
        assertThat(exercisesDTO1).isNotEqualTo(exercisesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(exercisesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(exercisesMapper.fromId(null)).isNull();
    }
}
