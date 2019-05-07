package com.thanglongedu.learnsql.service.mapper;

import com.thanglongedu.learnsql.domain.*;
import com.thanglongedu.learnsql.service.dto.ExercisesAnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExercisesAnswer and its DTO ExercisesAnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {ExercisesMapper.class})
public interface ExercisesAnswerMapper extends EntityMapper<ExercisesAnswerDTO, ExercisesAnswer> {

    @Mapping(source = "exercises.id", target = "exercisesId")
    ExercisesAnswerDTO toDto(ExercisesAnswer exercisesAnswer);

    @Mapping(source = "exercisesId", target = "exercises")
    ExercisesAnswer toEntity(ExercisesAnswerDTO exercisesAnswerDTO);

    default ExercisesAnswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExercisesAnswer exercisesAnswer = new ExercisesAnswer();
        exercisesAnswer.setId(id);
        return exercisesAnswer;
    }
}
