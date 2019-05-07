package com.thanglongedu.learnsql.web.rest;
import com.thanglongedu.learnsql.service.TypeContentService;
import com.thanglongedu.learnsql.web.rest.errors.BadRequestAlertException;
import com.thanglongedu.learnsql.web.rest.util.HeaderUtil;
import com.thanglongedu.learnsql.service.dto.TypeContentDTO;
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
 * REST controller for managing TypeContent.
 */
@RestController
@RequestMapping("/api")
public class TypeContentResource {

    private final Logger log = LoggerFactory.getLogger(TypeContentResource.class);

    private static final String ENTITY_NAME = "typeContent";

    private final TypeContentService typeContentService;

    public TypeContentResource(TypeContentService typeContentService) {
        this.typeContentService = typeContentService;
    }

    /**
     * POST  /type-contents : Create a new typeContent.
     *
     * @param typeContentDTO the typeContentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeContentDTO, or with status 400 (Bad Request) if the typeContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-contents")
    public ResponseEntity<TypeContentDTO> createTypeContent(@Valid @RequestBody TypeContentDTO typeContentDTO) throws URISyntaxException {
        log.debug("REST request to save TypeContent : {}", typeContentDTO);
        if (typeContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeContentDTO result = typeContentService.save(typeContentDTO);
        return ResponseEntity.created(new URI("/api/type-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-contents : Updates an existing typeContent.
     *
     * @param typeContentDTO the typeContentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeContentDTO,
     * or with status 400 (Bad Request) if the typeContentDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeContentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-contents")
    public ResponseEntity<TypeContentDTO> updateTypeContent(@Valid @RequestBody TypeContentDTO typeContentDTO) throws URISyntaxException {
        log.debug("REST request to update TypeContent : {}", typeContentDTO);
        if (typeContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeContentDTO result = typeContentService.save(typeContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-contents : get all the typeContents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeContents in body
     */
    @GetMapping("/type-contents")
    public List<TypeContentDTO> getAllTypeContents() {
        log.debug("REST request to get all TypeContents");
        return typeContentService.findAll();
    }

    /**
     * GET  /type-contents/:id : get the "id" typeContent.
     *
     * @param id the id of the typeContentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeContentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-contents/{id}")
    public ResponseEntity<TypeContentDTO> getTypeContent(@PathVariable Long id) {
        log.debug("REST request to get TypeContent : {}", id);
        Optional<TypeContentDTO> typeContentDTO = typeContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeContentDTO);
    }

    /**
     * DELETE  /type-contents/:id : delete the "id" typeContent.
     *
     * @param id the id of the typeContentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-contents/{id}")
    public ResponseEntity<Void> deleteTypeContent(@PathVariable Long id) {
        log.debug("REST request to delete TypeContent : {}", id);
        typeContentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-contents?query=:query : search for the typeContent corresponding
     * to the query.
     *
     * @param query the query of the typeContent search
     * @return the result of the search
     */
    @GetMapping("/_search/type-contents")
    public List<TypeContentDTO> searchTypeContents(@RequestParam String query) {
        log.debug("REST request to search TypeContents for query {}", query);
        return typeContentService.search(query);
    }

}
