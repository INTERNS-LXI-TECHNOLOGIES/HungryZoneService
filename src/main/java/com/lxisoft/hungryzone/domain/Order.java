package com.lxisoft.hungryzone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private ZonedDateTime orderDate;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "cart", "foods", "donatedOrders", "recievedOrders", "chats" }, allowSetters = true)
    private UserExtra donor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "cart", "foods", "donatedOrders", "recievedOrders", "chats" }, allowSetters = true)
    private UserExtra recipient;

    @ManyToMany
    @JoinTable(
        name = "rel_jhi_order__food",
        joinColumns = @JoinColumn(name = "jhi_order_id"),
        inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    @JsonIgnoreProperties(value = { "category", "donor", "orders" }, allowSetters = true)
    private Set<Food> foods = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return this.orderDate;
    }

    public Order orderDate(ZonedDateTime orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Order quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public Order orderStatus(String orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserExtra getDonor() {
        return this.donor;
    }

    public void setDonor(UserExtra userExtra) {
        this.donor = userExtra;
    }

    public Order donor(UserExtra userExtra) {
        this.setDonor(userExtra);
        return this;
    }

    public UserExtra getRecipient() {
        return this.recipient;
    }

    public void setRecipient(UserExtra userExtra) {
        this.recipient = userExtra;
    }

    public Order recipient(UserExtra userExtra) {
        this.setRecipient(userExtra);
        return this;
    }

    public Set<Food> getFoods() {
        return this.foods;
    }

    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }

    public Order foods(Set<Food> foods) {
        this.setFoods(foods);
        return this;
    }

    public Order addFood(Food food) {
        this.foods.add(food);
        food.getOrders().add(this);
        return this;
    }

    public Order removeFood(Food food) {
        this.foods.remove(food);
        food.getOrders().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", quantity=" + getQuantity() +
            ", orderStatus='" + getOrderStatus() + "'" +
            "}";
    }
}
