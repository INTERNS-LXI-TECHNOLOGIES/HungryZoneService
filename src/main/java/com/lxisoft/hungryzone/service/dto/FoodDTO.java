package com.lxisoft.hungryzone.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.lxisoft.hungryzone.domain.Food} entity.
 */
public class FoodDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private ZonedDateTime expiry;

    @NotNull
    private Integer remainingQty;

    private String description;

    private String imageUrl;

    private CategoryDTO category;

    private UserExtraDTO donor;

    private Set<OrderDTO> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(ZonedDateTime expiry) {
        this.expiry = expiry;
    }

    public Integer getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Integer remainingQty) {
        this.remainingQty = remainingQty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public UserExtraDTO getDonor() {
        return donor;
    }

    public void setDonor(UserExtraDTO donor) {
        this.donor = donor;
    }

    public Set<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDTO> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodDTO)) {
            return false;
        }

        FoodDTO foodDTO = (FoodDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, foodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", expiry='" + getExpiry() + "'" +
            ", remainingQty=" + getRemainingQty() +
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", category=" + getCategory() +
            ", donor=" + getDonor() +
            ", orders=" + getOrders() +
            "}";
    }
}
