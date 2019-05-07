package com.thanglongedu.learnsql.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A TypeContent.
 */
@Entity
@Table(name = "type_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typecontent")
public class TypeContent implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_type_content", nullable = false)
    private String nameTypeContent;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

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

    @OneToMany(mappedBy = "typeContent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> typeContentIDS = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameTypeContent() {
        return nameTypeContent;
    }

    public TypeContent nameTypeContent(String nameTypeContent) {
        this.nameTypeContent = nameTypeContent;
        return this;
    }

    public void setNameTypeContent(String nameTypeContent) {
        this.nameTypeContent = nameTypeContent;
    }

    public Integer getPriority() {
        return priority;
    }

    public TypeContent priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public TypeContent createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public TypeContent createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public TypeContent updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public TypeContent updatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Content> getTypeContentIDS() {
        return typeContentIDS;
    }

    public TypeContent typeContentIDS(Set<Content> contents) {
        this.typeContentIDS = contents;
        return this;
    }

    public TypeContent addTypeContentID(Content content) {
        this.typeContentIDS.add(content);
        content.setTypeContent(this);
        return this;
    }

    public TypeContent removeTypeContentID(Content content) {
        this.typeContentIDS.remove(content);
        content.setTypeContent(null);
        return this;
    }

    public void setTypeContentIDS(Set<Content> contents) {
        this.typeContentIDS = contents;
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
        TypeContent typeContent = (TypeContent) o;
        if (typeContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeContent{" +
            "id=" + getId() +
            ", nameTypeContent='" + getNameTypeContent() + "'" +
            ", priority=" + getPriority() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
