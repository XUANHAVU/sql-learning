package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.ExercisesAnswerService;
import com.thanglongedu.learnsql.domain.ExercisesAnswer;
import com.thanglongedu.learnsql.repository.ExercisesAnswerRepository;
import com.thanglongedu.learnsql.repository.search.ExercisesAnswerSearchRepository;
import com.thanglongedu.learnsql.service.dto.ExercisesAnswerDTO;
import com.thanglongedu.learnsql.service.mapper.ExercisesAnswerMapper;
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
 * Service Implementation for managing ExercisesAnswer.
 */
@Service
@Transactional
public class ExercisesAnswerServiceImpl implements ExercisesAnswerService {

    private final Logger log = LoggerFactory.getLogger(ExercisesAnswerServiceImpl.class);

    private final ExercisesAnswerRepository exercisesAnswerRepository;

    private final ExercisesAnswerMapper exercisesAnswerMapper;

    private final ExercisesAnswerSearchRepository exercisesAnswerSearchRepository;

    public ExercisesAnswerServiceImpl(ExercisesAnswerRepository exercisesAnswerRepository, ExercisesAnswerMapper exercisesAnswerMapper, ExercisesAnswerSearchRepository exercisesAnswerSearchRepository) {
        this.exercisesAnswerRepository = exercisesAnswerRepository;
        this.exercisesAnswerMapper = exercisesAnswerMapper;
        this.exercisesAnswerSearchRepository = exercisesAnswerSearchRepository;
    }

    /**
     * Save a exercisesAnswer.
     *
     * @param exercisesAnswerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExercisesAnswerDTO save(ExercisesAnswerDTO exercisesAnswerDTO) {
        log.debug("Request to save ExercisesAnswer : {}", exercisesAnswerDTO);
        ExercisesAnswer exercisesAnswer = exercisesAnswerMapper.toEntity(exercisesAnswerDTO);
        exercisesAnswer = exercisesAnswerRepository.save(exercisesAnswer);
        ExercisesAnswerDTO result = exercisesAnswerMapper.toDto(exercisesAnswer);
        exercisesAnswerSearchRepository.save(exercisesAnswer);
        return result;
    }

    /**
     * Get all the exercisesAnswers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExercisesAnswerDTO> findAll() {
        log.debug("Request to get all ExercisesAnswers");
        return exercisesAnswerRepository.findAll().stream()
            .map(exercisesAnswerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one exercisesAnswer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExercisesAnswerDTO> findOne(Long id) {
        log.debug("Request to get ExercisesAnswer : {}", id);
        return exercisesAnswerRepository.findById(id)
            .map(exercisesAnswerMapper::toDto);
    }

    /**
     * Delete the exercisesAnswer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExercisesAnswer : {}", id);
        exercisesAnswerRepository.deleteById(id);
        exercisesAnswerSearchRepository.deleteById(id);
    }

    /**
     * Search for the exercisesAnswer corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExercisesAnswerDTO> search(String query) {
        log.debug("Request to search ExercisesAnswers for query {}", query);
        return StreamSupport
            .stream(exercisesAnswerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(exercisesAnswerMapper::toDto)
            .collect(Collectors.toList());
    }
}
