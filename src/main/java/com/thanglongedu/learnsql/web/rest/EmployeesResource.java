package com.thanglongedu.learnsql.web.rest;
import com.thanglongedu.learnsql.service.EmployeesService;
import com.thanglongedu.learnsql.web.rest.errors.BadRequestAlertException;
import com.thanglongedu.learnsql.web.rest.util.HeaderUtil;
import com.thanglongedu.learnsql.service.dto.EmployeesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Employees.
 */
@RestController
@RequestMapping("/api")
public class EmployeesResource {

    private final Logger log = LoggerFactory.getLogger(EmployeesResource.class);

    private static final String ENTITY_NAME = "employees";

    private final EmployeesService employeesService;

    public EmployeesResource(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    /**
     * POST  /employees : Create a new employees.
     *
     * @param employeesDTO the employeesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeesDTO, or with status 400 (Bad Request) if the employees has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employees")
    public ResponseEntity<EmployeesDTO> createEmployees(@Valid @RequestBody EmployeesDTO employeesDTO) throws URISyntaxException {
        log.debug("REST request to save Employees : {}", employeesDTO);
        if (employeesDTO.getId() != null) {
            throw new BadRequestAlertException("A new employees cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeesDTO result = employeesService.save(employeesDTO);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employees : Updates an existing employees.
     *
     * @param employeesDTO the employeesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeesDTO,
     * or with status 400 (Bad Request) if the employeesDTO is not valid,
     * or with status 500 (Internal Server Error) if the employeesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employees")
    public ResponseEntity<EmployeesDTO> updateEmployees(@Valid @RequestBody EmployeesDTO employeesDTO) throws URISyntaxException {
        log.debug("REST request to update Employees : {}", employeesDTO);
        if (employeesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeesDTO result = employeesService.save(employeesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employees : get all the employees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employees in body
     */
    @GetMapping("/employees")
    public List<EmployeesDTO> getAllEmployees() {
        log.debug("REST request to get all Employees");
        return employeesService.findAll();
    }

    /**
     * GET  /employees/:id : get the "id" employees.
     *
     * @param id the id of the employeesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeesDTO> getEmployees(@PathVariable Long id) {
        log.debug("REST request to get Employees : {}", id);
        Optional<EmployeesDTO> employeesDTO = employeesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeesDTO);
    }

    /**
     * DELETE  /employees/:id : delete the "id" employees.
     *
     * @param id the id of the employeesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployees(@PathVariable Long id) {
        log.debug("REST request to delete Employees : {}", id);
        employeesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/employees?query=:query : search for the employees corresponding
     * to the query.
     *
     * @param query the query of the employees search
     * @return the result of the search
     */
    @GetMapping("/_search/employees")
    public List<EmployeesDTO> searchEmployees(@RequestParam String query) {
        log.debug("REST request to search Employees for query {}", query);
        return employeesService.search(query);
    }

}
