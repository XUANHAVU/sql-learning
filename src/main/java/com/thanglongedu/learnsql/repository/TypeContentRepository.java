package com.thanglongedu.learnsql.repository;

import com.thanglongedu.learnsql.domain.TypeContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeContentRepository extends JpaRepository<TypeContent, Long> {

}
