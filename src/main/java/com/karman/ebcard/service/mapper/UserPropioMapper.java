package com.karman.ebcard.service.mapper;

import com.karman.ebcard.domain.*;
import com.karman.ebcard.service.dto.UserPropioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserPropio and its DTO UserPropioDTO.
 */
@Mapper(componentModel = "spring", uses = {EBCardMapper.class})
public interface UserPropioMapper extends EntityMapper<UserPropioDTO, UserPropio> {


    @Mapping(target = "personals", ignore = true)
    @Mapping(target = "review", ignore = true)
    UserPropio toEntity(UserPropioDTO userPropioDTO);

    default UserPropio fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserPropio userPropio = new UserPropio();
        userPropio.setId(id);
        return userPropio;
    }
}
