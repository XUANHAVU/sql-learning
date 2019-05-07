package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.CategoryType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CategoryType entity.
 */
public interface CategoryTypeSearchRepository extends ElasticsearchRepository<CategoryType, Long> {
}
