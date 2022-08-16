package com.lxisoft.hungryzone.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lxisoft.hungryzone.domain.Cart} entity.
 */
public class CartDTO implements Serializable {

    private Long id;

    private UserExtraDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserExtraDTO getUser() {
        return user;
    }

    public void setUser(UserExtraDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartDTO)) {
            return false;
        }

        CartDTO cartDTO = (CartDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cartDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartDTO{" +
            "id=" + getId() +
            ", user=" + getUser() +
            "}";
    }
}
