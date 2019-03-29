package com.karman.ebcard.service.mapper;

import com.karman.ebcard.domain.*;
import com.karman.ebcard.service.dto.SocialContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SocialContact and its DTO SocialContactDTO.
 */
@Mapper(componentModel = "spring", uses = {EBCardMapper.class})
public interface SocialContactMapper extends EntityMapper<SocialContactDTO, SocialContact> {

    @Mapping(source = "EBCard.id", target = "EBCardId")
    SocialContactDTO toDto(SocialContact socialContact);

    @Mapping(source = "EBCardId", target = "EBCard")
    SocialContact toEntity(SocialContactDTO socialContactDTO);

    default SocialContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        SocialContact socialContact = new SocialContact();
        socialContact.setId(id);
        return socialContact;
    }
}
