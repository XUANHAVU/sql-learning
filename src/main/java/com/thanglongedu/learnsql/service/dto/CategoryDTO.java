package com.thanglongedu.learnsql.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Category entity.
 */
public class CategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameCategory;

    @NotNull
    private String title;

    private Boolean isKeyword;

    private String description;

    private String nameTableData;

    @NotNull
    private Instant createdDate;

    @NotNull
    private Integer createdBy;

    @NotNull
    private Instant updatedDate;

    @NotNull
    private Integer updatedBy;


    private Long categoryTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isIsKeyword() {
        return isKeyword;
    }

    public void setIsKeyword(Boolean isKeyword) {
        this.isKeyword = isKeyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameTableData() {
        return nameTableData;
    }

    public void setNameTableData(String nameTableData) {
        this.nameTableData = nameTableData;
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

    public Long getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Long categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (categoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", nameCategory='" + getNameCategory() + "'" +
            ", title='" + getTitle() + "'" +
            ", isKeyword='" + isIsKeyword() + "'" +
            ", description='" + getDescription() + "'" +
            ", nameTableData='" + getNameTableData() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", categoryType=" + getCategoryTypeId() +
            "}";
    }
}
