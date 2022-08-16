package com.lxisoft.hungryzone.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.lxisoft.hungryzone.domain.Chat} entity.
 */
public class ChatDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime chatDate;

    @NotNull
    private String textMessage;

    private Set<UserExtraDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<UserExtraDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserExtraDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatDTO)) {
            return false;
        }

        ChatDTO chatDTO = (ChatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatDTO{" +
            "id=" + getId() +
            ", chatDate='" + getChatDate() + "'" +
            ", textMessage='" + getTextMessage() + "'" +
            ", users=" + getUsers() +
            "}";
    }
}
