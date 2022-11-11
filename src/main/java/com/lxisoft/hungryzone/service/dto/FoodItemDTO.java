package com.lxisoft.hungryzone.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.lxisoft.hungryzone.domain.FoodItem} entity.
 */
public class FoodItemDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private String unit;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodItemDTO)) {
            return false;
        }

        FoodItemDTO foodItemDTO = (FoodItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, foodItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
