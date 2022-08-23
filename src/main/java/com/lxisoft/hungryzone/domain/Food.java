package com.lxisoft.hungryzone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Food.
 */
@Entity
@Table(name = "food")
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "expiry", nullable = false)
    private ZonedDateTime expiry;

    @NotNull
    @Column(name = "remaining_qty", nullable = false)
    private Integer remainingQty;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = { "foods" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "cart", "foods", "donatedOrders", "receivedOrders", "chats" }, allowSetters = true)
    private UserExtra donor;

    @ManyToMany(mappedBy = "foods")
    @JsonIgnoreProperties(value = { "donor", "recipient", "foods" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Food id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Food name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getExpiry() {
        return this.expiry;
    }

    public Food expiry(ZonedDateTime expiry) {
        this.setExpiry(expiry);
        return this;
    }

    public void setExpiry(ZonedDateTime expiry) {
        this.expiry = expiry;
    }

    public Integer getRemainingQty() {
        return this.remainingQty;
    }

    public Food remainingQty(Integer remainingQty) {
        this.setRemainingQty(remainingQty);
        return this;
    }

    public void setRemainingQty(Integer remainingQty) {
        this.remainingQty = remainingQty;
    }

    public String getDescription() {
        return this.description;
    }

    public Food description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Food category(Category category) {
        this.setCategory(category);
        return this;
    }

    public UserExtra getDonor() {
        return this.donor;
    }

    public void setDonor(UserExtra userExtra) {
        this.donor = userExtra;
    }

    public Food donor(UserExtra userExtra) {
        this.setDonor(userExtra);
        return this;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.removeFood(this));
        }
        if (orders != null) {
            orders.forEach(i -> i.addFood(this));
        }
        this.orders = orders;
    }

    public Food orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Food addOrder(Order order) {
        this.orders.add(order);
        order.getFoods().add(this);
        return this;
    }

    public Food removeOrder(Order order) {
        this.orders.remove(order);
        order.getFoods().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Food)) {
            return false;
        }
        return id != null && id.equals(((Food) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Food{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", expiry='" + getExpiry() + "'" +
            ", remainingQty=" + getRemainingQty() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
