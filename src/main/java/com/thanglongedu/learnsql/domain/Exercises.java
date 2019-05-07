package com.thanglongedu.learnsql.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Exercises.
 */
@Entity
@Table(name = "exercises")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "exercises")
public class Exercises implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_exercises", nullable = false)
    private String nameExercises;

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull
    @Column(name = "time_on_minutes", nullable = false)
    private Integer timeOnMinutes;

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
    @JsonIgnoreProperties("categoryIDS")
    private Category category;

    @OneToMany(mappedBy = "exercises")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExercisesAnswer> exercisesIDS = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameExercises() {
        return nameExercises;
    }

    public Exercises nameExercises(String nameExercises) {
        this.nameExercises = nameExercises;
        return this;
    }

    public void setNameExercises(String nameExercises) {
        this.nameExercises = nameExercises;
    }

    public String getQuestion() {
        return question;
    }

    public Exercises question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getScore() {
        return score;
    }

    public Exercises score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTimeOnMinutes() {
        return timeOnMinutes;
    }

    public Exercises timeOnMinutes(Integer timeOnMinutes) {
        this.timeOnMinutes = timeOnMinutes;
        return this;
    }

    public void setTimeOnMinutes(Integer timeOnMinutes) {
        this.timeOnMinutes = timeOnMinutes;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Exercises createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Exercises createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Exercises updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public Exercises updatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Category getCategory() {
        return category;
    }

    public Exercises category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<ExercisesAnswer> getExercisesIDS() {
        return exercisesIDS;
    }

    public Exercises exercisesIDS(Set<ExercisesAnswer> exercisesAnswers) {
        this.exercisesIDS = exercisesAnswers;
        return this;
    }

    public Exercises addExercisesID(ExercisesAnswer exercisesAnswer) {
        this.exercisesIDS.add(exercisesAnswer);
        exercisesAnswer.setExercises(this);
        return this;
    }

    public Exercises removeExercisesID(ExercisesAnswer exercisesAnswer) {
        this.exercisesIDS.remove(exercisesAnswer);
        exercisesAnswer.setExercises(null);
        return this;
    }

    public void setExercisesIDS(Set<ExercisesAnswer> exercisesAnswers) {
        this.exercisesIDS = exercisesAnswers;
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
        Exercises exercises = (Exercises) o;
        if (exercises.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exercises.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Exercises{" +
            "id=" + getId() +
            ", nameExercises='" + getNameExercises() + "'" +
            ", question='" + getQuestion() + "'" +
            ", score=" + getScore() +
            ", timeOnMinutes=" + getTimeOnMinutes() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
