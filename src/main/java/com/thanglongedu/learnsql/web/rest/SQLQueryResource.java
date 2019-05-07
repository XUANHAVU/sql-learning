package com.thanglongedu.learnsql.web.rest;
import com.thanglongedu.learnsql.service.SQLQueryService;
import com.thanglongedu.learnsql.web.rest.errors.BadRequestAlertException;
import com.thanglongedu.learnsql.web.rest.util.HeaderUtil;
import com.thanglongedu.learnsql.service.dto.SQLQueryDTO;
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
 * REST controller for managing SQLQuery.
 */
@RestController
@RequestMapping("/api")
public class SQLQueryResource {

    private final Logger log = LoggerFactory.getLogger(SQLQueryResource.class);

    private static final String ENTITY_NAME = "sQLQuery";

    private final SQLQueryService sQLQueryService;

    public SQLQueryResource(SQLQueryService sQLQueryService) {
        this.sQLQueryService = sQLQueryService;
    }

    /**
     * POST  /sql-queries : Create a new sQLQuery.
     *
     * @param sQLQueryDTO the sQLQueryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sQLQueryDTO, or with status 400 (Bad Request) if the sQLQuery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sql-queries")
    public ResponseEntity<SQLQueryDTO> createSQLQuery(@Valid @RequestBody SQLQueryDTO sQLQueryDTO) throws URISyntaxException {
        log.debug("REST request to save SQLQuery : {}", sQLQueryDTO);
        if (sQLQueryDTO.getId() != null) {
            throw new BadRequestAlertException("A new sQLQuery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SQLQueryDTO result = sQLQueryService.save(sQLQueryDTO);
        return ResponseEntity.created(new URI("/api/sql-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sql-queries : Updates an existing sQLQuery.
     *
     * @param sQLQueryDTO the sQLQueryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sQLQueryDTO,
     * or with status 400 (Bad Request) if the sQLQueryDTO is not valid,
     * or with status 500 (Internal Server Error) if the sQLQueryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sql-queries")
    public ResponseEntity<SQLQueryDTO> updateSQLQuery(@Valid @RequestBody SQLQueryDTO sQLQueryDTO) throws URISyntaxException {
        log.debug("REST request to update SQLQuery : {}", sQLQueryDTO);
        if (sQLQueryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SQLQueryDTO result = sQLQueryService.save(sQLQueryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sQLQueryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sql-queries : get all the sQLQueries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sQLQueries in body
     */
    @GetMapping("/sql-queries")
    public List<SQLQueryDTO> getAllSQLQueries() {
        log.debug("REST request to get all SQLQueries");
        return sQLQueryService.findAll();
    }

    /**
     * GET  /sql-queries/:id : get the "id" sQLQuery.
     *
     * @param id the id of the sQLQueryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sQLQueryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sql-queries/{id}")
    public ResponseEntity<SQLQueryDTO> getSQLQuery(@PathVariable Long id) {
        log.debug("REST request to get SQLQuery : {}", id);
        Optional<SQLQueryDTO> sQLQueryDTO = sQLQueryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sQLQueryDTO);
    }

    /**
     * DELETE  /sql-queries/:id : delete the "id" sQLQuery.
     *
     * @param id the id of the sQLQueryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sql-queries/{id}")
    public ResponseEntity<Void> deleteSQLQuery(@PathVariable Long id) {
        log.debug("REST request to delete SQLQuery : {}", id);
        sQLQueryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sql-queries?query=:query : search for the sQLQuery corresponding
     * to the query.
     *
     * @param query the query of the sQLQuery search
     * @return the result of the search
     */
    @GetMapping("/_search/sql-queries")
    public List<SQLQueryDTO> searchSQLQueries(@RequestParam String query) {
        log.debug("REST request to search SQLQueries for query {}", query);
        return sQLQueryService.search(query);
    }

}
