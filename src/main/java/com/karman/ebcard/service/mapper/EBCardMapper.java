package com.karman.ebcard.service.mapper;

import com.karman.ebcard.domain.*;
import com.karman.ebcard.service.dto.EBCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EBCard and its DTO EBCardDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserPropioMapper.class})
public interface EBCardMapper extends EntityMapper<EBCardDTO, EBCard> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "userPropio.id", target = "userPropioId")
    EBCardDTO toDto(EBCard eBCard);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "socialContacts", ignore = true)
    @Mapping(target = "phoneNumbers", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    @Mapping(target = "users", ignore = true)
    @Mapping(source = "userPropioId", target = "userPropio")
    EBCard toEntity(EBCardDTO eBCardDTO);

    default EBCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        EBCard eBCard = new EBCard();
        eBCard.setId(id);
        return eBCard;
    }
}
