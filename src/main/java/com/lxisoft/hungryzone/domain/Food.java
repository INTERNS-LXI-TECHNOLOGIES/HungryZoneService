package com.lxisoft.hungryzone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
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

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonIgnoreProperties(value = { "receivers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private FoodItem food;

    @ManyToOne
    @JsonIgnoreProperties(value = { "foods" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "cart", "foods", "receivedOrders" }, allowSetters = true)
    private UserExtra donor;

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

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Food imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public FoodItem getFood() {
        return this.food;
    }

    public void setFood(FoodItem foodItem) {
        this.food = foodItem;
    }

    public Food food(FoodItem foodItem) {
        this.setFood(foodItem);
        return this;
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
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
