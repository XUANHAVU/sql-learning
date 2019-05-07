package com.thanglongedu.learnsql.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SQLQuerySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SQLQuerySearchRepositoryMockConfiguration {

    @MockBean
    private SQLQuerySearchRepository mockSQLQuerySearchRepository;

}
