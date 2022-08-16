package com.lxisoft.hungryzone.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.lxisoft.hungryzone.domain.CartItem} entity.
 */
public class CartItemDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    private FoodDTO food;

    private CartDTO cart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public FoodDTO getFood() {
        return food;
    }

    public void setFood(FoodDTO food) {
        this.food = food;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItemDTO)) {
            return false;
        }

        CartItemDTO cartItemDTO = (CartItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cartItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", food=" + getFood() +
            ", cart=" + getCart() +
            "}";
    }
}
