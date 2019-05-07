package com.thanglongedu.learnsql.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of TypeContentSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TypeContentSearchRepositoryMockConfiguration {

    @MockBean
    private TypeContentSearchRepository mockTypeContentSearchRepository;

}
