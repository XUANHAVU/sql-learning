package com.thanglongedu.learnsql.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Employees entity.
 */
public class EmployeesDTO implements Serializable {

    private Long id;

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    @NotNull
    private Instant birthDate;

    @NotNull
    private String notes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeesDTO employeesDTO = (EmployeesDTO) o;
        if (employeesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeesDTO{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
