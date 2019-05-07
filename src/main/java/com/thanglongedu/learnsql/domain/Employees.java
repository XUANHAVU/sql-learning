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
 * A Employees.
 */
@Entity
@Table(name = "employees")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employees")
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private Instant birthDate;

    @NotNull
    @Column(name = "notes", nullable = false)
    private String notes;

    @OneToMany(mappedBy = "employees")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> employeesIDS = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public Employees lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employees firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public Employees birthDate(Instant birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public Employees notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Orders> getEmployeesIDS() {
        return employeesIDS;
    }

    public Employees employeesIDS(Set<Orders> orders) {
        this.employeesIDS = orders;
        return this;
    }

    public Employees addEmployeesID(Orders orders) {
        this.employeesIDS.add(orders);
        orders.setEmployees(this);
        return this;
    }

    public Employees removeEmployeesID(Orders orders) {
        this.employeesIDS.remove(orders);
        orders.setEmployees(null);
        return this;
    }

    public void setEmployeesIDS(Set<Orders> orders) {
        this.employeesIDS = orders;
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
        Employees employees = (Employees) o;
        if (employees.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employees.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employees{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
