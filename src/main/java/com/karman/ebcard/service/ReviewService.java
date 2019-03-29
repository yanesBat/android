package com.karman.ebcard.service;

import com.karman.ebcard.domain.Review;
import com.karman.ebcard.repository.ReviewRepository;
import com.karman.ebcard.service.dto.ReviewDTO;
import com.karman.ebcard.service.mapper.ReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Review.
 */
@Service
@Transactional
public class ReviewService {

    private final Logger log = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    /**
     * Save a review.
     *
     * @param reviewDTO the entity to save
     * @return the persisted entity
     */
    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Request to save Review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        review = reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    /**
     * Get all the reviews.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reviews");
        return reviewRepository.findAll(pageable)
            .map(reviewMapper::toDto);
    }


    /**
     * Get one review by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ReviewDTO> findOne(Long id) {
        log.debug("Request to get Review : {}", id);
        return reviewRepository.findById(id)
            .map(reviewMapper::toDto);
    }

    /**
     * Delete the review by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        reviewRepository.deleteById(id);
    }
}
