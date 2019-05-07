package com.thanglongedu.learnsql.web.rest;
import com.thanglongedu.learnsql.service.ShipperService;
import com.thanglongedu.learnsql.web.rest.errors.BadRequestAlertException;
import com.thanglongedu.learnsql.web.rest.util.HeaderUtil;
import com.thanglongedu.learnsql.service.dto.ShipperDTO;
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
 * REST controller for managing Shipper.
 */
@RestController
@RequestMapping("/api")
public class ShipperResource {

    private final Logger log = LoggerFactory.getLogger(ShipperResource.class);

    private static final String ENTITY_NAME = "shipper";

    private final ShipperService shipperService;

    public ShipperResource(ShipperService shipperService) {
        this.shipperService = shipperService;
    }

    /**
     * POST  /shippers : Create a new shipper.
     *
     * @param shipperDTO the shipperDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipperDTO, or with status 400 (Bad Request) if the shipper has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shippers")
    public ResponseEntity<ShipperDTO> createShipper(@Valid @RequestBody ShipperDTO shipperDTO) throws URISyntaxException {
        log.debug("REST request to save Shipper : {}", shipperDTO);
        if (shipperDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipperDTO result = shipperService.save(shipperDTO);
        return ResponseEntity.created(new URI("/api/shippers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shippers : Updates an existing shipper.
     *
     * @param shipperDTO the shipperDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipperDTO,
     * or with status 400 (Bad Request) if the shipperDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipperDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shippers")
    public ResponseEntity<ShipperDTO> updateShipper(@Valid @RequestBody ShipperDTO shipperDTO) throws URISyntaxException {
        log.debug("REST request to update Shipper : {}", shipperDTO);
        if (shipperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipperDTO result = shipperService.save(shipperDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipperDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shippers : get all the shippers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shippers in body
     */
    @GetMapping("/shippers")
    public List<ShipperDTO> getAllShippers() {
        log.debug("REST request to get all Shippers");
        return shipperService.findAll();
    }

    /**
     * GET  /shippers/:id : get the "id" shipper.
     *
     * @param id the id of the shipperDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipperDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shippers/{id}")
    public ResponseEntity<ShipperDTO> getShipper(@PathVariable Long id) {
        log.debug("REST request to get Shipper : {}", id);
        Optional<ShipperDTO> shipperDTO = shipperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipperDTO);
    }

    /**
     * DELETE  /shippers/:id : delete the "id" shipper.
     *
     * @param id the id of the shipperDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shippers/{id}")
    public ResponseEntity<Void> deleteShipper(@PathVariable Long id) {
        log.debug("REST request to delete Shipper : {}", id);
        shipperService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shippers?query=:query : search for the shipper corresponding
     * to the query.
     *
     * @param query the query of the shipper search
     * @return the result of the search
     */
    @GetMapping("/_search/shippers")
    public List<ShipperDTO> searchShippers(@RequestParam String query) {
        log.debug("REST request to search Shippers for query {}", query);
        return shipperService.search(query);
    }

}
