package com.lxisoft.hungryzone.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChatMapperTest {

    private ChatMapper chatMapper;

    @BeforeEach
    public void setUp() {
        chatMapper = new ChatMapperImpl();
    }
}
