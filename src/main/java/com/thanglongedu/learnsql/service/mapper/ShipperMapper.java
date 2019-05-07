package com.thanglongedu.learnsql.service.mapper;

import com.thanglongedu.learnsql.domain.*;
import com.thanglongedu.learnsql.service.dto.ShipperDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shipper and its DTO ShipperDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShipperMapper extends EntityMapper<ShipperDTO, Shipper> {


    @Mapping(target = "shipperIDS", ignore = true)
    Shipper toEntity(ShipperDTO shipperDTO);

    default Shipper fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shipper shipper = new Shipper();
        shipper.setId(id);
        return shipper;
    }
}
