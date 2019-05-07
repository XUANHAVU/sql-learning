package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.Employees;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Employees entity.
 */
public interface EmployeesSearchRepository extends ElasticsearchRepository<Employees, Long> {
}
