package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Food;
import com.lxisoft.hungryzone.domain.Order;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.FoodDTO;
import com.lxisoft.hungryzone.service.dto.OrderDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "donor", source = "donor", qualifiedByName = "userExtraId")
    @Mapping(target = "recipient", source = "recipient", qualifiedByName = "userExtraId")
    @Mapping(target = "foods", source = "foods", qualifiedByName = "foodIdSet")
    OrderDTO toDto(Order s);

    @Mapping(target = "removeFood", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @Named("userExtraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserExtraDTO toDtoUserExtraId(UserExtra userExtra);

    @Named("foodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FoodDTO toDtoFoodId(Food food);

    @Named("foodIdSet")
    default Set<FoodDTO> toDtoFoodIdSet(Set<Food> food) {
        return food.stream().map(this::toDtoFoodId).collect(Collectors.toSet());
    }
}
