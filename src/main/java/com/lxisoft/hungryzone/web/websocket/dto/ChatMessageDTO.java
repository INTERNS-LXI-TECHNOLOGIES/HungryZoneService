package com.lxisoft.hungryzone.web.websocket.dto;

import java.time.Instant;

public class ChatMessageDTO {

    private String userLogin;
    private String content;
    private Instant time;

    public ChatMessageDTO() {}

    public ChatMessageDTO(String userLogin, String content, Instant time) {
        this.userLogin = userLogin;
        this.content = content;
        this.time = time;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
