package com.lxisoft.hungryzone.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.lxisoft.hungryzone.domain.Message} entity.
 */
public class MessageDTO implements Serializable {

    private Long id;

    @NotNull
    private String userLogin;

    @NotNull
    private ZonedDateTime chatDate;

    @NotNull
    private String textMessage;

    private OrderDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public ZonedDateTime getChatDate() {
        return chatDate;
    }

    public void setChatDate(ZonedDateTime chatDate) {
        this.chatDate = chatDate;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public OrderDTO getUser() {
        return user;
    }

    public void setUser(OrderDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDTO)) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, messageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", chatDate='" + getChatDate() + "'" +
            ", textMessage='" + getTextMessage() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
