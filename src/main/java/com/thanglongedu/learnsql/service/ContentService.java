package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.ContentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Content.
 */
public interface ContentService {

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save
     * @return the persisted entity
     */
    ContentDTO save(ContentDTO contentDTO);

    /**
     * Get all the contents.
     *
     * @return the list of entities
     */
    List<ContentDTO> findAll();


    /**
     * Get the "id" content.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContentDTO> findOne(Long id);

    /**
     * Delete the "id" content.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the content corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ContentDTO> search(String query);
}
