package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.SQLQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SQLQuery entity.
 */
public interface SQLQuerySearchRepository extends ElasticsearchRepository<SQLQuery, Long> {
}
