package com.thanglongedu.learnsql.service.mapper;

import com.thanglongedu.learnsql.domain.*;
import com.thanglongedu.learnsql.service.dto.ExercisesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Exercises and its DTO ExercisesDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ExercisesMapper extends EntityMapper<ExercisesDTO, Exercises> {

    @Mapping(source = "category.id", target = "categoryId")
    ExercisesDTO toDto(Exercises exercises);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(target = "exercisesIDS", ignore = true)
    Exercises toEntity(ExercisesDTO exercisesDTO);

    default Exercises fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exercises exercises = new Exercises();
        exercises.setId(id);
        return exercises;
    }
}
