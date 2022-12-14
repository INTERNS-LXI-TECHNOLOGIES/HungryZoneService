package com.lxisoft.hungryzone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "location_at_x_axis", nullable = false)
    private String locationAtXAxis;

    @NotNull
    @Column(name = "location_at_y_axis", nullable = false)
    private String locationAtYAxis;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "user", "cartItems" }, allowSetters = true)
    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "donor")
    @JsonIgnoreProperties(value = { "food", "category", "donor" }, allowSetters = true)
    private Set<Food> foods = new HashSet<>();

    @OneToMany(mappedBy = "recipient")
    @JsonIgnoreProperties(value = { "users", "food", "recipient" }, allowSetters = true)
    private Set<Order> receivedOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserExtra id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public UserExtra phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public UserExtra address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationAtXAxis() {
        return this.locationAtXAxis;
    }

    public UserExtra locationAtXAxis(String locationAtXAxis) {
        this.setLocationAtXAxis(locationAtXAxis);
        return this;
    }

    public void setLocationAtXAxis(String locationAtXAxis) {
        this.locationAtXAxis = locationAtXAxis;
    }

    public String getLocationAtYAxis() {
        return this.locationAtYAxis;
    }

    public UserExtra locationAtYAxis(String locationAtYAxis) {
        this.setLocationAtYAxis(locationAtYAxis);
        return this;
    }

    public void setLocationAtYAxis(String locationAtYAxis) {
        this.locationAtYAxis = locationAtYAxis;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserExtra user(User user) {
        this.setUser(user);
        return this;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        if (this.cart != null) {
            this.cart.setUser(null);
        }
        if (cart != null) {
            cart.setUser(this);
        }
        this.cart = cart;
    }

    public UserExtra cart(Cart cart) {
        this.setCart(cart);
        return this;
    }

    public Set<Food> getFoods() {
        return this.foods;
    }

    public void setFoods(Set<Food> foods) {
        if (this.foods != null) {
            this.foods.forEach(i -> i.setDonor(null));
        }
        if (foods != null) {
            foods.forEach(i -> i.setDonor(this));
        }
        this.foods = foods;
    }

    public UserExtra foods(Set<Food> foods) {
        this.setFoods(foods);
        return this;
    }

    public UserExtra addFood(Food food) {
        this.foods.add(food);
        food.setDonor(this);
        return this;
    }

    public UserExtra removeFood(Food food) {
        this.foods.remove(food);
        food.setDonor(null);
        return this;
    }

    public Set<Order> getReceivedOrders() {
        return this.receivedOrders;
    }

    public void setReceivedOrders(Set<Order> orders) {
        if (this.receivedOrders != null) {
            this.receivedOrders.forEach(i -> i.setRecipient(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setRecipient(this));
        }
        this.receivedOrders = orders;
    }

    public UserExtra receivedOrders(Set<Order> orders) {
        this.setReceivedOrders(orders);
        return this;
    }

    public UserExtra addReceivedOrders(Order order) {
        this.receivedOrders.add(order);
        order.setRecipient(this);
        return this;
    }

    public UserExtra removeReceivedOrders(Order order) {
        this.receivedOrders.remove(order);
        order.setRecipient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtra)) {
            return false;
        }
        return id != null && id.equals(((UserExtra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", locationAtXAxis='" + getLocationAtXAxis() + "'" +
            ", locationAtYAxis='" + getLocationAtYAxis() + "'" +
            "}";
    }
}
