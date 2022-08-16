package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Cart;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.CartDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cart} and its DTO {@link CartDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDTO, Cart> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userExtraId")
    CartDTO toDto(Cart s);

    @Named("userExtraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserExtraDTO toDtoUserExtraId(UserExtra userExtra);
}
