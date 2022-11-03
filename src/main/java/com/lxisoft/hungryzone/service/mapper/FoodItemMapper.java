package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.FoodItem;
import com.lxisoft.hungryzone.service.dto.FoodItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoodItem} and its DTO {@link FoodItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface FoodItemMapper extends EntityMapper<FoodItemDTO, FoodItem> {}
