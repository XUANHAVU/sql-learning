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
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_category", nullable = false)
    private String nameCategory;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_keyword")
    private Boolean isKeyword;

    @Column(name = "description")
    private String description;

    @Column(name = "name_table_data")
    private String nameTableData;

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
    @JsonIgnoreProperties("categoryTypeIDS")
    private CategoryType categoryType;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> cateIDS = new HashSet<>();
    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exercises> categoryIDS = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public Category nameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getTitle() {
        return title;
    }

    public Category title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isIsKeyword() {
        return isKeyword;
    }

    public Category isKeyword(Boolean isKeyword) {
        this.isKeyword = isKeyword;
        return this;
    }

    public void setIsKeyword(Boolean isKeyword) {
        this.isKeyword = isKeyword;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameTableData() {
        return nameTableData;
    }

    public Category nameTableData(String nameTableData) {
        this.nameTableData = nameTableData;
        return this;
    }

    public void setNameTableData(String nameTableData) {
        this.nameTableData = nameTableData;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Category createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Category createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Category updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public Category updatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public Category categoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
        return this;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public Set<Content> getCateIDS() {
        return cateIDS;
    }

    public Category cateIDS(Set<Content> contents) {
        this.cateIDS = contents;
        return this;
    }

    public Category addCateID(Content content) {
        this.cateIDS.add(content);
        content.setCategory(this);
        return this;
    }

    public Category removeCateID(Content content) {
        this.cateIDS.remove(content);
        content.setCategory(null);
        return this;
    }

    public void setCateIDS(Set<Content> contents) {
        this.cateIDS = contents;
    }

    public Set<Exercises> getCategoryIDS() {
        return categoryIDS;
    }

    public Category categoryIDS(Set<Exercises> exercises) {
        this.categoryIDS = exercises;
        return this;
    }

    public Category addCategoryID(Exercises exercises) {
        this.categoryIDS.add(exercises);
        exercises.setCategory(this);
        return this;
    }

    public Category removeCategoryID(Exercises exercises) {
        this.categoryIDS.remove(exercises);
        exercises.setCategory(null);
        return this;
    }

    public void setCategoryIDS(Set<Exercises> exercises) {
        this.categoryIDS = exercises;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
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
            "}";
    }
}
