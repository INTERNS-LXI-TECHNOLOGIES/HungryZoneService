package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Category;
import com.lxisoft.hungryzone.domain.Food;
import com.lxisoft.hungryzone.domain.Order;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.CategoryDTO;
import com.lxisoft.hungryzone.service.dto.FoodDTO;
import com.lxisoft.hungryzone.service.dto.OrderDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Food} and its DTO {@link FoodDTO}.
 */
@Mapper(componentModel = "spring")
public interface FoodMapper extends EntityMapper<FoodDTO, Food> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "donor", source = "donor", qualifiedByName = "userExtraId")
    @Mapping(target = "orders", source = "orders", qualifiedByName = "orderIdSet")
    FoodDTO toDto(Food s);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("userExtraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserExtraDTO toDtoUserExtraId(UserExtra userExtra);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "orderStatus", source = "orderStatus")
    OrderDTO toDtoOrderId(Order order);

    @Named("orderIdSet")
    default Set<OrderDTO> toDtoOrderIdSet(Set<Order> order) {
        return order.stream().map(this::toDtoOrderId).collect(Collectors.toSet());
    }
}
