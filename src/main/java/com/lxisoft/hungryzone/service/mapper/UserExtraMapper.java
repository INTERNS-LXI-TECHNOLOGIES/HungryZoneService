package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Food;
import com.lxisoft.hungryzone.domain.User;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.FoodDTO;
import com.lxisoft.hungryzone.service.dto.UserDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserExtra} and its DTO {@link UserExtraDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserExtraMapper extends EntityMapper<UserExtraDTO, UserExtra> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "foods", source = "foods", qualifiedByName = "foodIdSet")
    UserExtraDTO toDto(UserExtra s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("foodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "expiry", source = "expiry")
    @Mapping(target = "remainingQty", source = "remainingQty")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "category", source = "category")
    FoodDTO toDtoFoodId(Food food);

    @Named("foodIdSet")
    default Set<FoodDTO> toDtoFoodIdSet(Set<Food> food) {
        return food.stream().map(this::toDtoFoodId).collect(Collectors.toSet());
    }
}
