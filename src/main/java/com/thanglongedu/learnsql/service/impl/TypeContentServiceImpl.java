package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.TypeContentService;
import com.thanglongedu.learnsql.domain.TypeContent;
import com.thanglongedu.learnsql.repository.TypeContentRepository;
import com.thanglongedu.learnsql.repository.search.TypeContentSearchRepository;
import com.thanglongedu.learnsql.service.dto.TypeContentDTO;
import com.thanglongedu.learnsql.service.mapper.TypeContentMapper;
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
 * Service Implementation for managing TypeContent.
 */
@Service
@Transactional
public class TypeContentServiceImpl implements TypeContentService {

    private final Logger log = LoggerFactory.getLogger(TypeContentServiceImpl.class);

    private final TypeContentRepository typeContentRepository;

    private final TypeContentMapper typeContentMapper;

    private final TypeContentSearchRepository typeContentSearchRepository;

    public TypeContentServiceImpl(TypeContentRepository typeContentRepository, TypeContentMapper typeContentMapper, TypeContentSearchRepository typeContentSearchRepository) {
        this.typeContentRepository = typeContentRepository;
        this.typeContentMapper = typeContentMapper;
        this.typeContentSearchRepository = typeContentSearchRepository;
    }

    /**
     * Save a typeContent.
     *
     * @param typeContentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeContentDTO save(TypeContentDTO typeContentDTO) {
        log.debug("Request to save TypeContent : {}", typeContentDTO);
        TypeContent typeContent = typeContentMapper.toEntity(typeContentDTO);
        typeContent = typeContentRepository.save(typeContent);
        TypeContentDTO result = typeContentMapper.toDto(typeContent);
        typeContentSearchRepository.save(typeContent);
        return result;
    }

    /**
     * Get all the typeContents.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeContentDTO> findAll() {
        log.debug("Request to get all TypeContents");
        return typeContentRepository.findAll().stream()
            .map(typeContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one typeContent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeContentDTO> findOne(Long id) {
        log.debug("Request to get TypeContent : {}", id);
        return typeContentRepository.findById(id)
            .map(typeContentMapper::toDto);
    }

    /**
     * Delete the typeContent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeContent : {}", id);
        typeContentRepository.deleteById(id);
        typeContentSearchRepository.deleteById(id);
    }

    /**
     * Search for the typeContent corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeContentDTO> search(String query) {
        log.debug("Request to search TypeContents for query {}", query);
        return StreamSupport
            .stream(typeContentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(typeContentMapper::toDto)
            .collect(Collectors.toList());
    }
}
