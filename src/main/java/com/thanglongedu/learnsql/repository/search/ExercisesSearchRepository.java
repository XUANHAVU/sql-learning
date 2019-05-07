package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.Exercises;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Exercises entity.
 */
public interface ExercisesSearchRepository extends ElasticsearchRepository<Exercises, Long> {
}
