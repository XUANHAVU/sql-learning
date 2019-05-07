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
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @ManyToOne
    @JsonIgnoreProperties("customerIDS")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("employeesIDS")
    private Employees employees;

    @ManyToOne
    @JsonIgnoreProperties("shipperIDS")
    private Shipper shipper;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public Orders orderDate(Instant orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Orders customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employees getEmployees() {
        return employees;
    }

    public Orders employees(Employees employees) {
        this.employees = employees;
        return this;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public Orders shipper(Shipper shipper) {
        this.shipper = shipper;
        return this;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
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
        Orders orders = (Orders) o;
        if (orders.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            "}";
    }
}
