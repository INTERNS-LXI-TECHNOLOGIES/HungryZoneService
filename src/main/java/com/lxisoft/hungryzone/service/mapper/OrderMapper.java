package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Order;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.OrderDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "donor", source = "donor", qualifiedByName = "userExtraId")
    @Mapping(target = "recipient", source = "recipient", qualifiedByName = "userExtraId")
    OrderDTO toDto(Order s);

    @Named("userExtraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserExtraDTO toDtoUserExtraId(UserExtra userExtra);
}
