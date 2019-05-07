package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.SQLQueryService;
import com.thanglongedu.learnsql.domain.SQLQuery;
import com.thanglongedu.learnsql.repository.SQLQueryRepository;
import com.thanglongedu.learnsql.repository.search.SQLQuerySearchRepository;
import com.thanglongedu.learnsql.service.dto.SQLQueryDTO;
import com.thanglongedu.learnsql.service.mapper.SQLQueryMapper;
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
 * Service Implementation for managing SQLQuery.
 */
@Service
@Transactional
public class SQLQueryServiceImpl implements SQLQueryService {

    private final Logger log = LoggerFactory.getLogger(SQLQueryServiceImpl.class);

    private final SQLQueryRepository sQLQueryRepository;

    private final SQLQueryMapper sQLQueryMapper;

    private final SQLQuerySearchRepository sQLQuerySearchRepository;

    public SQLQueryServiceImpl(SQLQueryRepository sQLQueryRepository, SQLQueryMapper sQLQueryMapper, SQLQuerySearchRepository sQLQuerySearchRepository) {
        this.sQLQueryRepository = sQLQueryRepository;
        this.sQLQueryMapper = sQLQueryMapper;
        this.sQLQuerySearchRepository = sQLQuerySearchRepository;
    }

    /**
     * Save a sQLQuery.
     *
     * @param sQLQueryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SQLQueryDTO save(SQLQueryDTO sQLQueryDTO) {
        log.debug("Request to save SQLQuery : {}", sQLQueryDTO);
        SQLQuery sQLQuery = sQLQueryMapper.toEntity(sQLQueryDTO);
        sQLQuery = sQLQueryRepository.save(sQLQuery);
        SQLQueryDTO result = sQLQueryMapper.toDto(sQLQuery);
        sQLQuerySearchRepository.save(sQLQuery);
        return result;
    }

    /**
     * Get all the sQLQueries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SQLQueryDTO> findAll() {
        log.debug("Request to get all SQLQueries");
        return sQLQueryRepository.findAll().stream()
            .map(sQLQueryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sQLQuery by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SQLQueryDTO> findOne(Long id) {
        log.debug("Request to get SQLQuery : {}", id);
        return sQLQueryRepository.findById(id)
            .map(sQLQueryMapper::toDto);
    }

    /**
     * Delete the sQLQuery by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SQLQuery : {}", id);
        sQLQueryRepository.deleteById(id);
        sQLQuerySearchRepository.deleteById(id);
    }

    /**
     * Search for the sQLQuery corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SQLQueryDTO> search(String query) {
        log.debug("Request to search SQLQueries for query {}", query);
        return StreamSupport
            .stream(sQLQuerySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(sQLQueryMapper::toDto)
            .collect(Collectors.toList());
    }
}
