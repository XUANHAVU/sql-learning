package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.ExercisesService;
import com.thanglongedu.learnsql.domain.Exercises;
import com.thanglongedu.learnsql.repository.ExercisesRepository;
import com.thanglongedu.learnsql.repository.search.ExercisesSearchRepository;
import com.thanglongedu.learnsql.service.dto.ExercisesDTO;
import com.thanglongedu.learnsql.service.mapper.ExercisesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Exercises.
 */
@Service
@Transactional
public class ExercisesServiceImpl implements ExercisesService {

    private final Logger log = LoggerFactory.getLogger(ExercisesServiceImpl.class);

    private final ExercisesRepository exercisesRepository;

    private final ExercisesMapper exercisesMapper;

    private final ExercisesSearchRepository exercisesSearchRepository;

    public ExercisesServiceImpl(ExercisesRepository exercisesRepository, ExercisesMapper exercisesMapper, ExercisesSearchRepository exercisesSearchRepository) {
        this.exercisesRepository = exercisesRepository;
        this.exercisesMapper = exercisesMapper;
        this.exercisesSearchRepository = exercisesSearchRepository;
    }

    /**
     * Save a exercises.
     *
     * @param exercisesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExercisesDTO save(ExercisesDTO exercisesDTO) {
        log.debug("Request to save Exercises : {}", exercisesDTO);
        Exercises exercises = exercisesMapper.toEntity(exercisesDTO);
        exercises = exercisesRepository.save(exercises);
        ExercisesDTO result = exercisesMapper.toDto(exercises);
        exercisesSearchRepository.save(exercises);
        return result;
    }

    /**
     * Get all the exercises.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExercisesDTO> findAll() {
        log.debug("Request to get all Exercises");
        return exercisesRepository.findAll().stream()
            .map(exercisesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one exercises by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExercisesDTO> findOne(Long id) {
        log.debug("Request to get Exercises : {}", id);
        return exercisesRepository.findById(id)
            .map(exercisesMapper::toDto);
    }

    /**
     * Delete the exercises by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exercises : {}", id);
        exercisesRepository.deleteById(id);
        exercisesSearchRepository.deleteById(id);
    }

    /**
     * Search for the exercises corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExercisesDTO> search(String query) {
        log.debug("Request to search Exercises for query {}", query);
        return StreamSupport
            .stream(exercisesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(exercisesMapper::toDto)
            .collect(Collectors.toList());
    }
}
