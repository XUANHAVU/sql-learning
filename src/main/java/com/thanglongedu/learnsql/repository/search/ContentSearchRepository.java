package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.Content;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Content entity.
 */
public interface ContentSearchRepository extends ElasticsearchRepository<Content, Long> {
}
