package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.ExercisesAnswerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ExercisesAnswer.
 */
public interface ExercisesAnswerService {

    /**
     * Save a exercisesAnswer.
     *
     * @param exercisesAnswerDTO the entity to save
     * @return the persisted entity
     */
    ExercisesAnswerDTO save(ExercisesAnswerDTO exercisesAnswerDTO);

    /**
     * Get all the exercisesAnswers.
     *
     * @return the list of entities
     */
    List<ExercisesAnswerDTO> findAll();


    /**
     * Get the "id" exercisesAnswer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ExercisesAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" exercisesAnswer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the exercisesAnswer corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ExercisesAnswerDTO> search(String query);
}
