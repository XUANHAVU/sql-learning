package com.thanglongedu.learnsql.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Shipper.
 */
@Entity
@Table(name = "shipper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shipper")
public class Shipper implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "shipper_name", nullable = false)
    private String shipperName;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "shipper")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> shipperIDS = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipperName() {
        return shipperName;
    }

    public Shipper shipperName(String shipperName) {
        this.shipperName = shipperName;
        return this;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getPhone() {
        return phone;
    }

    public Shipper phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Orders> getShipperIDS() {
        return shipperIDS;
    }

    public Shipper shipperIDS(Set<Orders> orders) {
        this.shipperIDS = orders;
        return this;
    }

    public Shipper addShipperID(Orders orders) {
        this.shipperIDS.add(orders);
        orders.setShipper(this);
        return this;
    }

    public Shipper removeShipperID(Orders orders) {
        this.shipperIDS.remove(orders);
        orders.setShipper(null);
        return this;
    }

    public void setShipperIDS(Set<Orders> orders) {
        this.shipperIDS = orders;
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
        Shipper shipper = (Shipper) o;
        if (shipper.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipper.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shipper{" +
            "id=" + getId() +
            ", shipperName='" + getShipperName() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
