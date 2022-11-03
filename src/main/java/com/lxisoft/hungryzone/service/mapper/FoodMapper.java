package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Category;
import com.lxisoft.hungryzone.domain.Food;
import com.lxisoft.hungryzone.domain.FoodItem;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.CategoryDTO;
import com.lxisoft.hungryzone.service.dto.FoodDTO;
import com.lxisoft.hungryzone.service.dto.FoodItemDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Food} and its DTO {@link FoodDTO}.
 */
@Mapper(componentModel = "spring")
public interface FoodMapper extends EntityMapper<FoodDTO, Food> {
    @Mapping(target = "food", source = "food", qualifiedByName = "foodItemId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "donor", source = "donor", qualifiedByName = "userExtraId")
    FoodDTO toDto(Food s);

    @Named("foodItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FoodItemDTO toDtoFoodItemId(FoodItem foodItem);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("userExtraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserExtraDTO toDtoUserExtraId(UserExtra userExtra);
}
