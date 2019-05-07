package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.EmployeesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Employees.
 */
public interface EmployeesService {

    /**
     * Save a employees.
     *
     * @param employeesDTO the entity to save
     * @return the persisted entity
     */
    EmployeesDTO save(EmployeesDTO employeesDTO);

    /**
     * Get all the employees.
     *
     * @return the list of entities
     */
    List<EmployeesDTO> findAll();


    /**
     * Get the "id" employees.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmployeesDTO> findOne(Long id);

    /**
     * Delete the "id" employees.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the employees corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<EmployeesDTO> search(String query);
}
