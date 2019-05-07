package com.thanglongedu.learnsql.web.rest;
import com.thanglongedu.learnsql.service.ContentService;
import com.thanglongedu.learnsql.web.rest.errors.BadRequestAlertException;
import com.thanglongedu.learnsql.web.rest.util.HeaderUtil;
import com.thanglongedu.learnsql.service.dto.ContentDTO;
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
 * REST controller for managing Content.
 */
@RestController
@RequestMapping("/api")
public class ContentResource {

    private final Logger log = LoggerFactory.getLogger(ContentResource.class);

    private static final String ENTITY_NAME = "content";

    private final ContentService contentService;

    public ContentResource(ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * POST  /contents : Create a new content.
     *
     * @param contentDTO the contentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentDTO, or with status 400 (Bad Request) if the content has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contents")
    public ResponseEntity<ContentDTO> createContent(@Valid @RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to save Content : {}", contentDTO);
        if (contentDTO.getId() != null) {
            throw new BadRequestAlertException("A new content cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentDTO result = contentService.save(contentDTO);
        return ResponseEntity.created(new URI("/api/contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contents : Updates an existing content.
     *
     * @param contentDTO the contentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentDTO,
     * or with status 400 (Bad Request) if the contentDTO is not valid,
     * or with status 500 (Internal Server Error) if the contentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contents")
    public ResponseEntity<ContentDTO> updateContent(@Valid @RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to update Content : {}", contentDTO);
        if (contentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentDTO result = contentService.save(contentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contents : get all the contents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contents in body
     */
    @GetMapping("/contents")
    public List<ContentDTO> getAllContents() {
        log.debug("REST request to get all Contents");
        return contentService.findAll();
    }

    /**
     * GET  /contents/:id : get the "id" content.
     *
     * @param id the id of the contentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contents/{id}")
    public ResponseEntity<ContentDTO> getContent(@PathVariable Long id) {
        log.debug("REST request to get Content : {}", id);
        Optional<ContentDTO> contentDTO = contentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentDTO);
    }

    /**
     * DELETE  /contents/:id : delete the "id" content.
     *
     * @param id the id of the contentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contents/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        log.debug("REST request to delete Content : {}", id);
        contentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/contents?query=:query : search for the content corresponding
     * to the query.
     *
     * @param query the query of the content search
     * @return the result of the search
     */
    @GetMapping("/_search/contents")
    public List<ContentDTO> searchContents(@RequestParam String query) {
        log.debug("REST request to search Contents for query {}", query);
        return contentService.search(query);
    }

}
