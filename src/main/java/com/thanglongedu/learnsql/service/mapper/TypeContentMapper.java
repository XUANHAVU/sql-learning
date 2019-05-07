package com.thanglongedu.learnsql.service.mapper;

import com.thanglongedu.learnsql.domain.*;
import com.thanglongedu.learnsql.service.dto.TypeContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeContent and its DTO TypeContentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeContentMapper extends EntityMapper<TypeContentDTO, TypeContent> {


    @Mapping(target = "typeContentIDS", ignore = true)
    TypeContent toEntity(TypeContentDTO typeContentDTO);

    default TypeContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeContent typeContent = new TypeContent();
        typeContent.setId(id);
        return typeContent;
    }
}
