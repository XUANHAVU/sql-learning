package com.thanglongedu.learnsql.service.impl;

import com.thanglongedu.learnsql.service.CategoryTypeService;
import com.thanglongedu.learnsql.domain.CategoryType;
import com.thanglongedu.learnsql.repository.CategoryTypeRepository;
import com.thanglongedu.learnsql.repository.search.CategoryTypeSearchRepository;
import com.thanglongedu.learnsql.service.dto.CategoryTypeDTO;
import com.thanglongedu.learnsql.service.mapper.CategoryTypeMapper;
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
 * Service Implementation for managing CategoryType.
 */
@Service
@Transactional
public class CategoryTypeServiceImpl implements CategoryTypeService {

    private final Logger log = LoggerFactory.getLogger(CategoryTypeServiceImpl.class);

    private final CategoryTypeRepository categoryTypeRepository;

    private final CategoryTypeMapper categoryTypeMapper;

    private final CategoryTypeSearchRepository categoryTypeSearchRepository;

    public CategoryTypeServiceImpl(CategoryTypeRepository categoryTypeRepository, CategoryTypeMapper categoryTypeMapper, CategoryTypeSearchRepository categoryTypeSearchRepository) {
        this.categoryTypeRepository = categoryTypeRepository;
        this.categoryTypeMapper = categoryTypeMapper;
        this.categoryTypeSearchRepository = categoryTypeSearchRepository;
    }

    /**
     * Save a categoryType.
     *
     * @param categoryTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoryTypeDTO save(CategoryTypeDTO categoryTypeDTO) {
        log.debug("Request to save CategoryType : {}", categoryTypeDTO);
        CategoryType categoryType = categoryTypeMapper.toEntity(categoryTypeDTO);
        categoryType = categoryTypeRepository.save(categoryType);
        CategoryTypeDTO result = categoryTypeMapper.toDto(categoryType);
        categoryTypeSearchRepository.save(categoryType);
        return result;
    }

    /**
     * Get all the categoryTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryTypeDTO> findAll() {
        log.debug("Request to get all CategoryTypes");
        return categoryTypeRepository.findAll().stream()
            .map(categoryTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one categoryType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryTypeDTO> findOne(Long id) {
        log.debug("Request to get CategoryType : {}", id);
        return categoryTypeRepository.findById(id)
            .map(categoryTypeMapper::toDto);
    }

    /**
     * Delete the categoryType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryType : {}", id);
        categoryTypeRepository.deleteById(id);
        categoryTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the categoryType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryTypeDTO> search(String query) {
        log.debug("Request to search CategoryTypes for query {}", query);
        return StreamSupport
            .stream(categoryTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(categoryTypeMapper::toDto)
            .collect(Collectors.toList());
    }
}
