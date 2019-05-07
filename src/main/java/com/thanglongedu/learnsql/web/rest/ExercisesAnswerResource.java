package com.thanglongedu.learnsql.web.rest;
import com.thanglongedu.learnsql.service.ExercisesAnswerService;
import com.thanglongedu.learnsql.web.rest.errors.BadRequestAlertException;
import com.thanglongedu.learnsql.web.rest.util.HeaderUtil;
import com.thanglongedu.learnsql.service.dto.ExercisesAnswerDTO;
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
 * REST controller for managing ExercisesAnswer.
 */
@RestController
@RequestMapping("/api")
public class ExercisesAnswerResource {

    private final Logger log = LoggerFactory.getLogger(ExercisesAnswerResource.class);

    private static final String ENTITY_NAME = "exercisesAnswer";

    private final ExercisesAnswerService exercisesAnswerService;

    public ExercisesAnswerResource(ExercisesAnswerService exercisesAnswerService) {
        this.exercisesAnswerService = exercisesAnswerService;
    }

    /**
     * POST  /exercises-answers : Create a new exercisesAnswer.
     *
     * @param exercisesAnswerDTO the exercisesAnswerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exercisesAnswerDTO, or with status 400 (Bad Request) if the exercisesAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exercises-answers")
    public ResponseEntity<ExercisesAnswerDTO> createExercisesAnswer(@Valid @RequestBody ExercisesAnswerDTO exercisesAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save ExercisesAnswer : {}", exercisesAnswerDTO);
        if (exercisesAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new exercisesAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExercisesAnswerDTO result = exercisesAnswerService.save(exercisesAnswerDTO);
        return ResponseEntity.created(new URI("/api/exercises-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercises-answers : Updates an existing exercisesAnswer.
     *
     * @param exercisesAnswerDTO the exercisesAnswerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exercisesAnswerDTO,
     * or with status 400 (Bad Request) if the exercisesAnswerDTO is not valid,
     * or with status 500 (Internal Server Error) if the exercisesAnswerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exercises-answers")
    public ResponseEntity<ExercisesAnswerDTO> updateExercisesAnswer(@Valid @RequestBody ExercisesAnswerDTO exercisesAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update ExercisesAnswer : {}", exercisesAnswerDTO);
        if (exercisesAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExercisesAnswerDTO result = exercisesAnswerService.save(exercisesAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exercisesAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercises-answers : get all the exercisesAnswers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of exercisesAnswers in body
     */
    @GetMapping("/exercises-answers")
    public List<ExercisesAnswerDTO> getAllExercisesAnswers() {
        log.debug("REST request to get all ExercisesAnswers");
        return exercisesAnswerService.findAll();
    }

    /**
     * GET  /exercises-answers/:id : get the "id" exercisesAnswer.
     *
     * @param id the id of the exercisesAnswerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exercisesAnswerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exercises-answers/{id}")
    public ResponseEntity<ExercisesAnswerDTO> getExercisesAnswer(@PathVariable Long id) {
        log.debug("REST request to get ExercisesAnswer : {}", id);
        Optional<ExercisesAnswerDTO> exercisesAnswerDTO = exercisesAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exercisesAnswerDTO);
    }

    /**
     * DELETE  /exercises-answers/:id : delete the "id" exercisesAnswer.
     *
     * @param id the id of the exercisesAnswerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exercises-answers/{id}")
    public ResponseEntity<Void> deleteExercisesAnswer(@PathVariable Long id) {
        log.debug("REST request to delete ExercisesAnswer : {}", id);
        exercisesAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/exercises-answers?query=:query : search for the exercisesAnswer corresponding
     * to the query.
     *
     * @param query the query of the exercisesAnswer search
     * @return the result of the search
     */
    @GetMapping("/_search/exercises-answers")
    public List<ExercisesAnswerDTO> searchExercisesAnswers(@RequestParam String query) {
        log.debug("REST request to search ExercisesAnswers for query {}", query);
        return exercisesAnswerService.search(query);
    }

}
