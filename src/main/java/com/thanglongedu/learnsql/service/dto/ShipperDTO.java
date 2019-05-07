package com.thanglongedu.learnsql.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Shipper entity.
 */
public class ShipperDTO implements Serializable {

    private Long id;

    @NotNull
    private String shipperName;

    @NotNull
    private String phone;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipperDTO shipperDTO = (ShipperDTO) o;
        if (shipperDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipperDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipperDTO{" +
            "id=" + getId() +
            ", shipperName='" + getShipperName() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
