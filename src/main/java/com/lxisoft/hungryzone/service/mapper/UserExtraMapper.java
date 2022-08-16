package com.lxisoft.hungryzone.service.mapper;

import com.lxisoft.hungryzone.domain.User;
import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.service.dto.UserDTO;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserExtra} and its DTO {@link UserExtraDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserExtraMapper extends EntityMapper<UserExtraDTO, UserExtra> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    UserExtraDTO toDto(UserExtra s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
