package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.Orders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Orders entity.
 */
public interface OrdersSearchRepository extends ElasticsearchRepository<Orders, Long> {
}
