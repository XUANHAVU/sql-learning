package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.OrdersService;
import com.thanglongedu.learnsql.domain.Orders;
import com.thanglongedu.learnsql.repository.OrdersRepository;
import com.thanglongedu.learnsql.repository.search.OrdersSearchRepository;
import com.thanglongedu.learnsql.service.dto.OrdersDTO;
import com.thanglongedu.learnsql.service.mapper.OrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Orders.
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    private final Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    private final OrdersSearchRepository ordersSearchRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper, OrdersSearchRepository ordersSearchRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.ordersSearchRepository = ordersSearchRepository;
    }

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrdersDTO save(OrdersDTO ordersDTO) {
        log.debug("Request to save Orders : {}", ordersDTO);
        Orders orders = ordersMapper.toEntity(ordersDTO);
        orders = ordersRepository.save(orders);
        OrdersDTO result = ordersMapper.toDto(orders);
        ordersSearchRepository.save(orders);
        return result;
    }

    /**
     * Get all the orders.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrdersDTO> findAll() {
        log.debug("Request to get all Orders");
        return ordersRepository.findAll().stream()
            .map(ordersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orders by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrdersDTO> findOne(Long id) {
        log.debug("Request to get Orders : {}", id);
        return ordersRepository.findById(id)
            .map(ordersMapper::toDto);
    }

    /**
     * Delete the orders by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Orders : {}", id);
        ordersRepository.deleteById(id);
        ordersSearchRepository.deleteById(id);
    }

    /**
     * Search for the orders corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrdersDTO> search(String query) {
        log.debug("Request to search Orders for query {}", query);
        return StreamSupport
            .stream(ordersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(ordersMapper::toDto)
            .collect(Collectors.toList());
    }
}
