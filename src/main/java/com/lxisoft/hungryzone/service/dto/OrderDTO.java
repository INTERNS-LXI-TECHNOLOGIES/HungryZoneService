package com.lxisoft.hungryzone.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.lxisoft.hungryzone.domain.Order} entity.
 */
public class OrderDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime orderDate;

    @NotNull
    private Integer quantity;

    @NotNull
    private String orderStatus;

    private UserExtraDTO donor;

    private UserExtraDTO recipient;

    private Set<FoodDTO> foods = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserExtraDTO getDonor() {
        return donor;
    }

    public void setDonor(UserExtraDTO donor) {
        this.donor = donor;
    }

    public UserExtraDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(UserExtraDTO recipient) {
        this.recipient = recipient;
    }

    public Set<FoodDTO> getFoods() {
        return foods;
    }

    public void setFoods(Set<FoodDTO> foods) {
        this.foods = foods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", quantity=" + getQuantity() +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", donor=" + getDonor() +
            ", recipient=" + getRecipient() +
            ", foods=" + getFoods() +
            "}";
    }
}
