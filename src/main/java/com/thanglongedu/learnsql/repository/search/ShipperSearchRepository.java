package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.Shipper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Shipper entity.
 */
public interface ShipperSearchRepository extends ElasticsearchRepository<Shipper, Long> {
}
