package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.ContentService;
import com.thanglongedu.learnsql.domain.Content;
import com.thanglongedu.learnsql.repository.ContentRepository;
import com.thanglongedu.learnsql.repository.search.ContentSearchRepository;
import com.thanglongedu.learnsql.service.dto.ContentDTO;
import com.thanglongedu.learnsql.service.mapper.ContentMapper;
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
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    private final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final ContentSearchRepository contentSearchRepository;

    public ContentServiceImpl(ContentRepository contentRepository, ContentMapper contentMapper, ContentSearchRepository contentSearchRepository) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.contentSearchRepository = contentSearchRepository;
    }

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContentDTO save(ContentDTO contentDTO) {
        log.debug("Request to save Content : {}", contentDTO);
        Content content = contentMapper.toEntity(contentDTO);
        content = contentRepository.save(content);
        ContentDTO result = contentMapper.toDto(content);
        contentSearchRepository.save(content);
        return result;
    }

    /**
     * Get all the contents.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContentDTO> findAll() {
        log.debug("Request to get all Contents");
        return contentRepository.findAll().stream()
            .map(contentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one content by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContentDTO> findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        return contentRepository.findById(id)
            .map(contentMapper::toDto);
    }

    /**
     * Delete the content by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.deleteById(id);
        contentSearchRepository.deleteById(id);
    }

    /**
     * Search for the content corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContentDTO> search(String query) {
        log.debug("Request to search Contents for query {}", query);
        return StreamSupport
            .stream(contentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(contentMapper::toDto)
            .collect(Collectors.toList());
    }
}
