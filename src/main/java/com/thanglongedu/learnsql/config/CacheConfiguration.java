package com.thanglongedu.learnsql.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.thanglongedu.learnsql.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.CategoryType.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.CategoryType.class.getName() + ".categoryTypeIDS", jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Category.class.getName() + ".cateIDS", jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Category.class.getName() + ".categoryIDS", jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Content.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.SQLQuery.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.TypeContent.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.TypeContent.class.getName() + ".typeContentIDS", jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Exercises.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Exercises.class.getName() + ".exercisesIDS", jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.ExercisesAnswer.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Orders.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Customer.class.getName() + ".customerIDS", jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Employees.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Employees.class.getName() + ".employeesIDS", jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Shipper.class.getName(), jcacheConfiguration);
            cm.createCache(com.thanglongedu.learnsql.domain.Shipper.class.getName() + ".shipperIDS", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
