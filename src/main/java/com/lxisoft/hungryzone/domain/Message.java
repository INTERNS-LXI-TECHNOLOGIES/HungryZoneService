package com.lxisoft.hungryzone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_login", nullable = false)
    private String userLogin;

    @NotNull
    @Column(name = "chat_date", nullable = false)
    private ZonedDateTime chatDate;

    @NotNull
    @Column(name = "text_message", nullable = false)
    private String textMessage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "users", "food", "recipient" }, allowSetters = true)
    private Order user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Message id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLogin() {
        return this.userLogin;
    }

    public Message userLogin(String userLogin) {
        this.setUserLogin(userLogin);
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public ZonedDateTime getChatDate() {
        return this.chatDate;
    }

    public Message chatDate(ZonedDateTime chatDate) {
        this.setChatDate(chatDate);
        return this;
    }

    public void setChatDate(ZonedDateTime chatDate) {
        this.chatDate = chatDate;
    }

    public String getTextMessage() {
        return this.textMessage;
    }

    public Message textMessage(String textMessage) {
        this.setTextMessage(textMessage);
        return this;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Order getUser() {
        return this.user;
    }

    public void setUser(Order order) {
        this.user = order;
    }

    public Message user(Order order) {
        this.setUser(order);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", chatDate='" + getChatDate() + "'" +
            ", textMessage='" + getTextMessage() + "'" +
            "}";
    }
}
