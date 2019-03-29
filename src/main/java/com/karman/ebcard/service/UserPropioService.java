package com.karman.ebcard.service;

import com.karman.ebcard.domain.UserPropio;
import com.karman.ebcard.repository.UserPropioRepository;
import com.karman.ebcard.service.dto.UserPropioDTO;
import com.karman.ebcard.service.mapper.UserPropioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing UserPropio.
 */
@Service
@Transactional
public class UserPropioService {

    private final Logger log = LoggerFactory.getLogger(UserPropioService.class);

    private final UserPropioRepository userPropioRepository;

    private final UserPropioMapper userPropioMapper;

    public UserPropioService(UserPropioRepository userPropioRepository, UserPropioMapper userPropioMapper) {
        this.userPropioRepository = userPropioRepository;
        this.userPropioMapper = userPropioMapper;
    }

    /**
     * Save a userPropio.
     *
     * @param userPropioDTO the entity to save
     * @return the persisted entity
     */
    public UserPropioDTO save(UserPropioDTO userPropioDTO) {
        log.debug("Request to save UserPropio : {}", userPropioDTO);
        UserPropio userPropio = userPropioMapper.toEntity(userPropioDTO);
        userPropio = userPropioRepository.save(userPropio);
        return userPropioMapper.toDto(userPropio);
    }

    /**
     * Get all the userPropios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserPropioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPropios");
        return userPropioRepository.findAll(pageable)
            .map(userPropioMapper::toDto);
    }

    /**
     * Get all the UserPropio with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<UserPropioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userPropioRepository.findAllWithEagerRelationships(pageable).map(userPropioMapper::toDto);
    }
    


    /**
     *  get all the userPropios where Review is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserPropioDTO> findAllWhereReviewIsNull() {
        log.debug("Request to get all userPropios where Review is null");
        return StreamSupport
            .stream(userPropioRepository.findAll().spliterator(), false)
            .filter(userPropio -> userPropio.getReview() == null)
            .map(userPropioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userPropio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserPropioDTO> findOne(Long id) {
        log.debug("Request to get UserPropio : {}", id);
        return userPropioRepository.findOneWithEagerRelationships(id)
            .map(userPropioMapper::toDto);
    }

    /**
     * Delete the userPropio by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPropio : {}", id);
        userPropioRepository.deleteById(id);
    }
}
