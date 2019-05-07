package com.thanglongedu.learnsql.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ExercisesSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ExercisesSearchRepositoryMockConfiguration {

    @MockBean
    private ExercisesSearchRepository mockExercisesSearchRepository;

}
