package com.thanglongedu.learnsql.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Exercises entity.
 */
public class ExercisesDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameExercises;

    @NotNull
    private String question;

    @NotNull
    private Integer score;

    @NotNull
    private Integer timeOnMinutes;

    @NotNull
    private Instant createdDate;

    @NotNull
    private Integer createdBy;

    @NotNull
    private Instant updatedDate;

    @NotNull
    private Integer updatedBy;


    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameExercises() {
        return nameExercises;
    }

    public void setNameExercises(String nameExercises) {
        this.nameExercises = nameExercises;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTimeOnMinutes() {
        return timeOnMinutes;
    }

    public void setTimeOnMinutes(Integer timeOnMinutes) {
        this.timeOnMinutes = timeOnMinutes;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExercisesDTO exercisesDTO = (ExercisesDTO) o;
        if (exercisesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exercisesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExercisesDTO{" +
            "id=" + getId() +
            ", nameExercises='" + getNameExercises() + "'" +
            ", question='" + getQuestion() + "'" +
            ", score=" + getScore() +
            ", timeOnMinutes=" + getTimeOnMinutes() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", category=" + getCategoryId() +
            "}";
    }
}
