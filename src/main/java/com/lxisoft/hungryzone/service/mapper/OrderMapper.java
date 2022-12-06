package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.FoodItem;
import com.lxisoft.hungryzone.domain.Order;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.FoodItemDTO;
import com.lxisoft.hungryzone.service.dto.OrderDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "food", source = "food", qualifiedByName = "foodItemId")
    @Mapping(target = "recipient", source = "recipient", qualifiedByName = "userExtraId")
    OrderDTO toDto(Order s);

    @Named("foodItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FoodItemDTO toDtoFoodItemId(FoodItem foodItem);

    @Named("userExtraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserExtraDTO toDtoUserExtraId(UserExtra userExtra);
}
