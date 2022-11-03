package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Message;
import com.lxisoft.hungryzone.domain.Order;
import com.lxisoft.hungryzone.service.dto.MessageDTO;
import com.lxisoft.hungryzone.service.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "user", source = "user", qualifiedByName = "orderId")
    MessageDTO toDto(Message s);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);
}
