package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.SQLQueryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SQLQuery.
 */
public interface SQLQueryService {

    /**
     * Save a sQLQuery.
     *
     * @param sQLQueryDTO the entity to save
     * @return the persisted entity
     */
    SQLQueryDTO save(SQLQueryDTO sQLQueryDTO);

    /**
     * Get all the sQLQueries.
     *
     * @return the list of entities
     */
    List<SQLQueryDTO> findAll();


    /**
     * Get the "id" sQLQuery.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SQLQueryDTO> findOne(Long id);

    /**
     * Delete the "id" sQLQuery.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sQLQuery corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<SQLQueryDTO> search(String query);
}
