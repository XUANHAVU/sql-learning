package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.TypeContentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TypeContent.
 */
public interface TypeContentService {

    /**
     * Save a typeContent.
     *
     * @param typeContentDTO the entity to save
     * @return the persisted entity
     */
    TypeContentDTO save(TypeContentDTO typeContentDTO);

    /**
     * Get all the typeContents.
     *
     * @return the list of entities
     */
    List<TypeContentDTO> findAll();


    /**
     * Get the "id" typeContent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypeContentDTO> findOne(Long id);

    /**
     * Delete the "id" typeContent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeContent corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TypeContentDTO> search(String query);
}
