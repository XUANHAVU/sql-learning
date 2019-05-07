package com.thanglongedu.learnsql.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ExercisesAnswer entity.
 */
public class ExercisesAnswerDTO implements Serializable {

    private Long id;

    @NotNull
    private String result;

    private Boolean isCorrect;

    @NotNull
    private Instant createdDate;

    @NotNull
    private Integer createdBy;

    @NotNull
    private Instant updatedDate;

    @NotNull
    private Integer updatedBy;


    private Long exercisesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getExercisesId() {
        return exercisesId;
    }

    public void setExercisesId(Long exercisesId) {
        this.exercisesId = exercisesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExercisesAnswerDTO exercisesAnswerDTO = (ExercisesAnswerDTO) o;
        if (exercisesAnswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exercisesAnswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExercisesAnswerDTO{" +
            "id=" + getId() +
            ", result='" + getResult() + "'" +
            ", isCorrect='" + isIsCorrect() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", exercises=" + getExercisesId() +
            "}";
    }
}
