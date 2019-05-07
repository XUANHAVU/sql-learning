package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.ShipperService;
import com.thanglongedu.learnsql.domain.Shipper;
import com.thanglongedu.learnsql.repository.ShipperRepository;
import com.thanglongedu.learnsql.repository.search.ShipperSearchRepository;
import com.thanglongedu.learnsql.service.dto.ShipperDTO;
import com.thanglongedu.learnsql.service.mapper.ShipperMapper;
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
 * Service Implementation for managing Shipper.
 */
@Service
@Transactional
public class ShipperServiceImpl implements ShipperService {

    private final Logger log = LoggerFactory.getLogger(ShipperServiceImpl.class);

    private final ShipperRepository shipperRepository;

    private final ShipperMapper shipperMapper;

    private final ShipperSearchRepository shipperSearchRepository;

    public ShipperServiceImpl(ShipperRepository shipperRepository, ShipperMapper shipperMapper, ShipperSearchRepository shipperSearchRepository) {
        this.shipperRepository = shipperRepository;
        this.shipperMapper = shipperMapper;
        this.shipperSearchRepository = shipperSearchRepository;
    }

    /**
     * Save a shipper.
     *
     * @param shipperDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipperDTO save(ShipperDTO shipperDTO) {
        log.debug("Request to save Shipper : {}", shipperDTO);
        Shipper shipper = shipperMapper.toEntity(shipperDTO);
        shipper = shipperRepository.save(shipper);
        ShipperDTO result = shipperMapper.toDto(shipper);
        shipperSearchRepository.save(shipper);
        return result;
    }

    /**
     * Get all the shippers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipperDTO> findAll() {
        log.debug("Request to get all Shippers");
        return shipperRepository.findAll().stream()
            .map(shipperMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shipper by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipperDTO> findOne(Long id) {
        log.debug("Request to get Shipper : {}", id);
        return shipperRepository.findById(id)
            .map(shipperMapper::toDto);
    }

    /**
     * Delete the shipper by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipper : {}", id);
        shipperRepository.deleteById(id);
        shipperSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipper corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipperDTO> search(String query) {
        log.debug("Request to search Shippers for query {}", query);
        return StreamSupport
            .stream(shipperSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shipperMapper::toDto)
            .collect(Collectors.toList());
    }
}
