package com.lxisoft.hungryzone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "chat_date", nullable = false)
    private ZonedDateTime chatDate;

    @NotNull
    @Column(name = "text_message", nullable = false)
    private String textMessage;

    @ManyToMany
    @JoinTable(name = "rel_chat__users", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    @JsonIgnoreProperties(value = { "user", "cart", "foods", "donatedOrders", "recievedOrders", "chats" }, allowSetters = true)
    private Set<UserExtra> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Chat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getChatDate() {
        return this.chatDate;
    }

    public Chat chatDate(ZonedDateTime chatDate) {
        this.setChatDate(chatDate);
        return this;
    }

    public void setChatDate(ZonedDateTime chatDate) {
        this.chatDate = chatDate;
    }

    public String getTextMessage() {
        return this.textMessage;
    }

    public Chat textMessage(String textMessage) {
        this.setTextMessage(textMessage);
        return this;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Set<UserExtra> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserExtra> userExtras) {
        this.users = userExtras;
    }

    public Chat users(Set<UserExtra> userExtras) {
        this.setUsers(userExtras);
        return this;
    }

    public Chat addUsers(UserExtra userExtra) {
        this.users.add(userExtra);
        userExtra.getChats().add(this);
        return this;
    }

    public Chat removeUsers(UserExtra userExtra) {
        this.users.remove(userExtra);
        userExtra.getChats().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chat)) {
            return false;
        }
        return id != null && id.equals(((Chat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chat{" +
            "id=" + getId() +
            ", chatDate='" + getChatDate() + "'" +
            ", textMessage='" + getTextMessage() + "'" +
            "}";
    }
}
