package com.thanglongedu.learnsql.service.mapper;

import com.thanglongedu.learnsql.domain.*;
import com.thanglongedu.learnsql.service.dto.SQLQueryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SQLQuery and its DTO SQLQueryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SQLQueryMapper extends EntityMapper<SQLQueryDTO, SQLQuery> {



    default SQLQuery fromId(Long id) {
        if (id == null) {
            return null;
        }
        SQLQuery sQLQuery = new SQLQuery();
        sQLQuery.setId(id);
        return sQLQuery;
    }
}
