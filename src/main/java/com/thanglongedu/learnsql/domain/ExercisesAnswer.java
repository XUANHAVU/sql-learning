package com.thanglongedu.learnsql.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ExercisesAnswer.
 */
@Entity
@Table(name = "exercises_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "exercisesanswer")
public class ExercisesAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @NotNull
    @Column(name = "updated_date", nullable = false)
    private Instant updatedDate;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy;

    @ManyToOne
    @JsonIgnoreProperties("exercisesIDS")
    private Exercises exercises;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public ExercisesAnswer result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean isIsCorrect() {
        return isCorrect;
    }

    public ExercisesAnswer isCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
        return this;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ExercisesAnswer createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public ExercisesAnswer createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public ExercisesAnswer updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public ExercisesAnswer updatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Exercises getExercises() {
        return exercises;
    }

    public ExercisesAnswer exercises(Exercises exercises) {
        this.exercises = exercises;
        return this;
    }

    public void setExercises(Exercises exercises) {
        this.exercises = exercises;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExercisesAnswer exercisesAnswer = (ExercisesAnswer) o;
        if (exercisesAnswer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exercisesAnswer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExercisesAnswer{" +
            "id=" + getId() +
            ", result='" + getResult() + "'" +
            ", isCorrect='" + isIsCorrect() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
