package com.lxisoft.hungryzone.domain;

import java.io.Serializable;
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
    @Column(name = "quandity", nullable = false)
    private Integer quandity;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

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

    public Integer getQuandity() {
        return this.quandity;
    }

    public FoodItem quandity(Integer quandity) {
        this.setQuandity(quandity);
        return this;
    }

    public void setQuandity(Integer quandity) {
        this.quandity = quandity;
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
            ", quandity=" + getQuandity() +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}