package com.lxisoft.hungryzone.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodItemMapperTest {

    private FoodItemMapper foodItemMapper;

    @BeforeEach
    public void setUp() {
        foodItemMapper = new FoodItemMapperImpl();
    }
}
