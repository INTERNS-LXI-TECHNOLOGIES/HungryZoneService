package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Cart;
import com.lxisoft.hungryzone.domain.CartItem;
import com.lxisoft.hungryzone.domain.Food;
import com.lxisoft.hungryzone.service.dto.CartDTO;
import com.lxisoft.hungryzone.service.dto.CartItemDTO;
import com.lxisoft.hungryzone.service.dto.FoodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CartItem} and its DTO {@link CartItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartItemMapper extends EntityMapper<CartItemDTO, CartItem> {
    @Mapping(target = "food", source = "food", qualifiedByName = "foodId")
    @Mapping(target = "cart", source = "cart", qualifiedByName = "cartId")
    CartItemDTO toDto(CartItem s);

    @Named("foodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FoodDTO toDtoFoodId(Food food);

    @Named("cartId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoCartId(Cart cart);
}
