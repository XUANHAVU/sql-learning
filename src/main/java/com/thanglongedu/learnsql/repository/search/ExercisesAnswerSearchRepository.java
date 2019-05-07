package com.thanglongedu.learnsql.repository.search;

import com.thanglongedu.learnsql.domain.ExercisesAnswer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ExercisesAnswer entity.
 */
public interface ExercisesAnswerSearchRepository extends ElasticsearchRepository<ExercisesAnswer, Long> {
}
