package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.CategoryTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CategoryType.
 */
public interface CategoryTypeService {

    /**
     * Save a categoryType.
     *
     * @param categoryTypeDTO the entity to save
     * @return the persisted entity
     */
    CategoryTypeDTO save(CategoryTypeDTO categoryTypeDTO);

    /**
     * Get all the categoryTypes.
     *
     * @return the list of entities
     */
    List<CategoryTypeDTO> findAll();


    /**
     * Get the "id" categoryType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CategoryTypeDTO> findOne(Long id);

    /**
     * Delete the "id" categoryType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the categoryType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CategoryTypeDTO> search(String query);
}
