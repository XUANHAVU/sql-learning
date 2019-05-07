package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.TypeContent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TypeContent entity.
 */
public interface TypeContentSearchRepository extends ElasticsearchRepository<TypeContent, Long> {
}
