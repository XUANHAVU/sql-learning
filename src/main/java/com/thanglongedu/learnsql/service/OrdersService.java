package com.thanglongedu.learnsql.service;

import com.thanglongedu.learnsql.service.dto.OrdersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Orders.
 */
public interface OrdersService {

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save
     * @return the persisted entity
     */
    OrdersDTO save(OrdersDTO ordersDTO);

    /**
     * Get all the orders.
     *
     * @return the list of entities
     */
    List<OrdersDTO> findAll();


    /**
     * Get the "id" orders.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrdersDTO> findOne(Long id);

    /**
     * Delete the "id" orders.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orders corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<OrdersDTO> search(String query);
}
