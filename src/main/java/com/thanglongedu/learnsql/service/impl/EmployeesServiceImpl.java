package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.EmployeesService;
import com.thanglongedu.learnsql.domain.Employees;
import com.thanglongedu.learnsql.repository.EmployeesRepository;
import com.thanglongedu.learnsql.repository.search.EmployeesSearchRepository;
import com.thanglongedu.learnsql.service.dto.EmployeesDTO;
import com.thanglongedu.learnsql.service.mapper.EmployeesMapper;
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
 * Service Implementation for managing Employees.
 */
@Service
@Transactional
public class EmployeesServiceImpl implements EmployeesService {

    private final Logger log = LoggerFactory.getLogger(EmployeesServiceImpl.class);

    private final EmployeesRepository employeesRepository;

    private final EmployeesMapper employeesMapper;

    private final EmployeesSearchRepository employeesSearchRepository;

    public EmployeesServiceImpl(EmployeesRepository employeesRepository, EmployeesMapper employeesMapper, EmployeesSearchRepository employeesSearchRepository) {
        this.employeesRepository = employeesRepository;
        this.employeesMapper = employeesMapper;
        this.employeesSearchRepository = employeesSearchRepository;
    }

    /**
     * Save a employees.
     *
     * @param employeesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeesDTO save(EmployeesDTO employeesDTO) {
        log.debug("Request to save Employees : {}", employeesDTO);
        Employees employees = employeesMapper.toEntity(employeesDTO);
        employees = employeesRepository.save(employees);
        EmployeesDTO result = employeesMapper.toDto(employees);
        employeesSearchRepository.save(employees);
        return result;
    }

    /**
     * Get all the employees.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeesDTO> findAll() {
        log.debug("Request to get all Employees");
        return employeesRepository.findAll().stream()
            .map(employeesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one employees by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeesDTO> findOne(Long id) {
        log.debug("Request to get Employees : {}", id);
        return employeesRepository.findById(id)
            .map(employeesMapper::toDto);
    }

    /**
     * Delete the employees by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employees : {}", id);
        employeesRepository.deleteById(id);
        employeesSearchRepository.deleteById(id);
    }

    /**
     * Search for the employees corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeesDTO> search(String query) {
        log.debug("Request to search Employees for query {}", query);
        return StreamSupport
            .stream(employeesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(employeesMapper::toDto)
            .collect(Collectors.toList());
    }
}
