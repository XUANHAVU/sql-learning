package com.thanglongedu.learnsql.web.rest;
import com.thanglongedu.learnsql.service.CategoryTypeService;
import com.thanglongedu.learnsql.web.rest.errors.BadRequestAlertException;
import com.thanglongedu.learnsql.web.rest.util.HeaderUtil;
import com.thanglongedu.learnsql.service.dto.CategoryTypeDTO;
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
 * REST controller for managing CategoryType.
 */
@RestController
@RequestMapping("/api")
public class CategoryTypeResource {

    private final Logger log = LoggerFactory.getLogger(CategoryTypeResource.class);

    private static final String ENTITY_NAME = "categoryType";

    private final CategoryTypeService categoryTypeService;

    public CategoryTypeResource(CategoryTypeService categoryTypeService) {
        this.categoryTypeService = categoryTypeService;
    }

    /**
     * POST  /category-types : Create a new categoryType.
     *
     * @param categoryTypeDTO the categoryTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoryTypeDTO, or with status 400 (Bad Request) if the categoryType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/category-types")
    public ResponseEntity<CategoryTypeDTO> createCategoryType(@Valid @RequestBody CategoryTypeDTO categoryTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryType : {}", categoryTypeDTO);
        if (categoryTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryTypeDTO result = categoryTypeService.save(categoryTypeDTO);
        return ResponseEntity.created(new URI("/api/category-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /category-types : Updates an existing categoryType.
     *
     * @param categoryTypeDTO the categoryTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoryTypeDTO,
     * or with status 400 (Bad Request) if the categoryTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the categoryTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/category-types")
    public ResponseEntity<CategoryTypeDTO> updateCategoryType(@Valid @RequestBody CategoryTypeDTO categoryTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryType : {}", categoryTypeDTO);
        if (categoryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryTypeDTO result = categoryTypeService.save(categoryTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoryTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /category-types : get all the categoryTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categoryTypes in body
     */
    @GetMapping("/category-types")
    public List<CategoryTypeDTO> getAllCategoryTypes() {
        log.debug("REST request to get all CategoryTypes");
        return categoryTypeService.findAll();
    }

    /**
     * GET  /category-types/:id : get the "id" categoryType.
     *
     * @param id the id of the categoryTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoryTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/category-types/{id}")
    public ResponseEntity<CategoryTypeDTO> getCategoryType(@PathVariable Long id) {
        log.debug("REST request to get CategoryType : {}", id);
        Optional<CategoryTypeDTO> categoryTypeDTO = categoryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryTypeDTO);
    }

    /**
     * DELETE  /category-types/:id : delete the "id" categoryType.
     *
     * @param id the id of the categoryTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/category-types/{id}")
    public ResponseEntity<Void> deleteCategoryType(@PathVariable Long id) {
        log.debug("REST request to delete CategoryType : {}", id);
        categoryTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/category-types?query=:query : search for the categoryType corresponding
     * to the query.
     *
     * @param query the query of the categoryType search
     * @return the result of the search
     */
    @GetMapping("/_search/category-types")
    public List<CategoryTypeDTO> searchCategoryTypes(@RequestParam String query) {
        log.debug("REST request to search CategoryTypes for query {}", query);
        return categoryTypeService.search(query);
    }

}
