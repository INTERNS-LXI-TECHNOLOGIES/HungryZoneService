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
    @Column(name = "unit", nullable = false)
    private String unit;

    @NotNull
    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<Message> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "receivers" }, allowSetters = true)
    private FoodItem food;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "cart", "foods", "receivedOrders" }, allowSetters = true)
    private UserExtra recipient;

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

    public String getUnit() {
        return this.unit;
    }

    public Order unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Set<Message> getUsers() {
        return this.users;
    }

    public void setUsers(Set<Message> messages) {
        if (this.users != null) {
            this.users.forEach(i -> i.setUser(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setUser(this));
        }
        this.users = messages;
    }

    public Order users(Set<Message> messages) {
        this.setUsers(messages);
        return this;
    }

    public Order addUser(Message message) {
        this.users.add(message);
        message.setUser(this);
        return this;
    }

    public Order removeUser(Message message) {
        this.users.remove(message);
        message.setUser(null);
        return this;
    }

    public FoodItem getFood() {
        return this.food;
    }

    public void setFood(FoodItem foodItem) {
        this.food = foodItem;
    }

    public Order food(FoodItem foodItem) {
        this.setFood(foodItem);
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
            ", unit='" + getUnit() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            "}";
    }
}
