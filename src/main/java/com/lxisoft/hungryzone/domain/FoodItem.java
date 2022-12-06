package com.lxisoft.hungryzone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A FoodItem.
 */
@Entity
@Table(name = "food_item")
public class FoodItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @OneToMany(mappedBy = "food")
    @JsonIgnoreProperties(value = { "users", "food", "recipient" }, allowSetters = true)
    private Set<Order> receivers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FoodItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public FoodItem quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return this.unit;
    }

    public FoodItem unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Set<Order> getReceivers() {
        return this.receivers;
    }

    public void setReceivers(Set<Order> orders) {
        if (this.receivers != null) {
            this.receivers.forEach(i -> i.setFood(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setFood(this));
        }
        this.receivers = orders;
    }

    public FoodItem receivers(Set<Order> orders) {
        this.setReceivers(orders);
        return this;
    }

    public FoodItem addReceiver(Order order) {
        this.receivers.add(order);
        order.setFood(this);
        return this;
    }

    public FoodItem removeReceiver(Order order) {
        this.receivers.remove(order);
        order.setFood(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodItem)) {
            return false;
        }
        return id != null && id.equals(((FoodItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
