package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.ShipperDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Shipper.
 */
public interface ShipperService {

    /**
     * Save a shipper.
     *
     * @param shipperDTO the entity to save
     * @return the persisted entity
     */
    ShipperDTO save(ShipperDTO shipperDTO);

    /**
     * Get all the shippers.
     *
     * @return the list of entities
     */
    List<ShipperDTO> findAll();


    /**
     * Get the "id" shipper.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipperDTO> findOne(Long id);

    /**
     * Delete the "id" shipper.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipper corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ShipperDTO> search(String query);
}
