package com.thanglongedu.learnsql.repository;

import com.thanglongedu.learnsql.domain.Shipper;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Shipper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Long> {

}
