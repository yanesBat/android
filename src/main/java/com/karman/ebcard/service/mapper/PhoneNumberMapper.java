package com.karman.ebcard.service.mapper;

import com.karman.ebcard.domain.*;
import com.karman.ebcard.service.dto.PhoneNumberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PhoneNumber and its DTO PhoneNumberDTO.
 */
@Mapper(componentModel = "spring", uses = {EBCardMapper.class})
public interface PhoneNumberMapper extends EntityMapper<PhoneNumberDTO, PhoneNumber> {

    @Mapping(source = "EBCard.id", target = "EBCardId")
    PhoneNumberDTO toDto(PhoneNumber phoneNumber);

    @Mapping(source = "EBCardId", target = "EBCard")
    PhoneNumber toEntity(PhoneNumberDTO phoneNumberDTO);

    default PhoneNumber fromId(Long id) {
        if (id == null) {
            return null;
        }
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(id);
        return phoneNumber;
    }
}
