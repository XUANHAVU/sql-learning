package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.ExercisesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Exercises.
 */
public interface ExercisesService {

    /**
     * Save a exercises.
     *
     * @param exercisesDTO the entity to save
     * @return the persisted entity
     */
    ExercisesDTO save(ExercisesDTO exercisesDTO);

    /**
     * Get all the exercises.
     *
     * @return the list of entities
     */
    List<ExercisesDTO> findAll();


    /**
     * Get the "id" exercises.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ExercisesDTO> findOne(Long id);

    /**
     * Delete the "id" exercises.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the exercises corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ExercisesDTO> search(String query);
}
