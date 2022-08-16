package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.Chat;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.ChatDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chat} and its DTO {@link ChatDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChatMapper extends EntityMapper<ChatDTO, Chat> {
    @Mapping(target = "users", source = "users", qualifiedByName = "userExtraIdSet")
    ChatDTO toDto(Chat s);

    @Mapping(target = "removeUsers", ignore = true)
    Chat toEntity(ChatDTO chatDTO);

    @Named("userExtraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserExtraDTO toDtoUserExtraId(UserExtra userExtra);

    @Named("userExtraIdSet")
    default Set<UserExtraDTO> toDtoUserExtraIdSet(Set<UserExtra> userExtra) {
        return userExtra.stream().map(this::toDtoUserExtraId).collect(Collectors.toSet());
    }
}
