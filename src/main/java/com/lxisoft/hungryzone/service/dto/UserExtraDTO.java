package com.lxisoft.hungryzone.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.lxisoft.hungryzone.domain.UserExtra} entity.
 */
public class UserExtraDTO implements Serializable {

    private Long id;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private String locationAtXAxis;

    @NotNull
    private String locationAtYAxis;

    private Set<FoodDTO> foods = new HashSet<>();

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationAtXAxis() {
        return locationAtXAxis;
    }

    public void setLocationAtXAxis(String locationAtXAxis) {
        this.locationAtXAxis = locationAtXAxis;
    }

    public String getLocationAtYAxis() {
        return locationAtYAxis;
    }

    public void setLocationAtYAxis(String locationAtYAxis) {
        this.locationAtYAxis = locationAtYAxis;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
        if (!(o instanceof UserExtraDTO)) {
            return false;
        }

        UserExtraDTO userExtraDTO = (UserExtraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userExtraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtraDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", locationAtXAxis='" + getLocationAtXAxis() + "'" +
            ", locationAtYAxis='" + getLocationAtYAxis() + "'" +
            ", user=" + getUser() +
            ", foods=" + getFoods() +
            "}";
    }
}
