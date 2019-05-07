package com.thanglongedu.learnsql.service.mapper;

import com.thanglongedu.learnsql.domain.*;
import com.thanglongedu.learnsql.service.dto.ContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Content and its DTO ContentDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, TypeContentMapper.class})
public interface ContentMapper extends EntityMapper<ContentDTO, Content> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "typeContent.id", target = "typeContentId")
    ContentDTO toDto(Content content);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "typeContentId", target = "typeContent")
    Content toEntity(ContentDTO contentDTO);

    default Content fromId(Long id) {
        if (id == null) {
            return null;
        }
        Content content = new Content();
        content.setId(id);
        return content;
    }
}
