package com.karman.ebcard.service.mapper;

import com.karman.ebcard.domain.*;
import com.karman.ebcard.service.dto.ReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {UserPropioMapper.class, EBCardMapper.class})
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {

    @Mapping(source = "userPropio.id", target = "userPropioId")
    @Mapping(source = "EBCard.id", target = "EBCardId")
    ReviewDTO toDto(Review review);

    @Mapping(source = "userPropioId", target = "userPropio")
    @Mapping(source = "EBCardId", target = "EBCard")
    Review toEntity(ReviewDTO reviewDTO);

    default Review fromId(Long id) {
        if (id == null) {
            return null;
        }
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
