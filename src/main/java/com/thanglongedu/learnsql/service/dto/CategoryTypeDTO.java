package com.thanglongedu.learnsql.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CategoryType entity.
 */
public class CategoryTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameCategoryType;

    private String description;

    @NotNull
    private Instant createdDate;

    @NotNull
    private Integer createdBy;

    @NotNull
    private Instant updatedDate;

    @NotNull
    private Integer updatedBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategoryType() {
        return nameCategoryType;
    }

    public void setNameCategoryType(String nameCategoryType) {
        this.nameCategoryType = nameCategoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryTypeDTO categoryTypeDTO = (CategoryTypeDTO) o;
        if (categoryTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryTypeDTO{" +
            "id=" + getId() +
            ", nameCategoryType='" + getNameCategoryType() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
