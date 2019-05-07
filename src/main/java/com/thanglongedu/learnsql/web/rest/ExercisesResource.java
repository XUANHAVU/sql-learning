package com.thanglongedu.learnsql.web.rest;
import com.thanglongedu.learnsql.service.ExercisesService;
import com.thanglongedu.learnsql.web.rest.errors.BadRequestAlertException;
import com.thanglongedu.learnsql.web.rest.util.HeaderUtil;
import com.thanglongedu.learnsql.service.dto.ExercisesDTO;
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
 * REST controller for managing Exercises.
 */
@RestController
@RequestMapping("/api")
public class ExercisesResource {

    private final Logger log = LoggerFactory.getLogger(ExercisesResource.class);

    private static final String ENTITY_NAME = "exercises";

    private final ExercisesService exercisesService;

    public ExercisesResource(ExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    /**
     * POST  /exercises : Create a new exercises.
     *
     * @param exercisesDTO the exercisesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exercisesDTO, or with status 400 (Bad Request) if the exercises has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercises")
    public ResponseEntity<ExercisesDTO> createExercises(@Valid @RequestBody ExercisesDTO exercisesDTO) throws URISyntaxException {
        log.debug("REST request to save Exercises : {}", exercisesDTO);
        if (exercisesDTO.getId() != null) {
            throw new BadRequestAlertException("A new exercises cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExercisesDTO result = exercisesService.save(exercisesDTO);
        return ResponseEntity.created(new URI("/api/exercises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercises : Updates an existing exercises.
     *
     * @param exercisesDTO the exercisesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exercisesDTO,
     * or with status 400 (Bad Request) if the exercisesDTO is not valid,
     * or with status 500 (Internal Server Error) if the exercisesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercises")
    public ResponseEntity<ExercisesDTO> updateExercises(@Valid @RequestBody ExercisesDTO exercisesDTO) throws URISyntaxException {
        log.debug("REST request to update Exercises : {}", exercisesDTO);
        if (exercisesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExercisesDTO result = exercisesService.save(exercisesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exercisesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercises : get all the exercises.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of exercises in body
     */
    @GetMapping("/exercises")
    public List<ExercisesDTO> getAllExercises() {
        log.debug("REST request to get all Exercises");
        return exercisesService.findAll();
    }

    /**
     * GET  /exercises/:id : get the "id" exercises.
     *
     * @param id the id of the exercisesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exercisesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercises/{id}")
    public ResponseEntity<ExercisesDTO> getExercises(@PathVariable Long id) {
        log.debug("REST request to get Exercises : {}", id);
        Optional<ExercisesDTO> exercisesDTO = exercisesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exercisesDTO);
    }

    /**
     * DELETE  /exercises/:id : delete the "id" exercises.
     *
     * @param id the id of the exercisesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercises/{id}")
    public ResponseEntity<Void> deleteExercises(@PathVariable Long id) {
        log.debug("REST request to delete Exercises : {}", id);
        exercisesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/exercises?query=:query : search for the exercises corresponding
     * to the query.
     *
     * @param query the query of the exercises search
     * @return the result of the search
     */
    @GetMapping("/_search/exercises")
    public List<ExercisesDTO> searchExercises(@RequestParam String query) {
        log.debug("REST request to search Exercises for query {}", query);
        return exercisesService.search(query);
    }

}
