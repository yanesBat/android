package com.karman.ebcard.service;

import com.karman.ebcard.domain.SocialContact;
import com.karman.ebcard.repository.SocialContactRepository;
import com.karman.ebcard.service.dto.SocialContactDTO;
import com.karman.ebcard.service.mapper.SocialContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SocialContact.
 */
@Service
@Transactional
public class SocialContactService {

    private final Logger log = LoggerFactory.getLogger(SocialContactService.class);

    private final SocialContactRepository socialContactRepository;

    private final SocialContactMapper socialContactMapper;

    public SocialContactService(SocialContactRepository socialContactRepository, SocialContactMapper socialContactMapper) {
        this.socialContactRepository = socialContactRepository;
        this.socialContactMapper = socialContactMapper;
    }

    /**
     * Save a socialContact.
     *
     * @param socialContactDTO the entity to save
     * @return the persisted entity
     */
    public SocialContactDTO save(SocialContactDTO socialContactDTO) {
        log.debug("Request to save SocialContact : {}", socialContactDTO);
        SocialContact socialContact = socialContactMapper.toEntity(socialContactDTO);
        socialContact = socialContactRepository.save(socialContact);
        return socialContactMapper.toDto(socialContact);
    }

    /**
     * Get all the socialContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SocialContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocialContacts");
        return socialContactRepository.findAll(pageable)
            .map(socialContactMapper::toDto);
    }


    /**
     * Get one socialContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SocialContactDTO> findOne(Long id) {
        log.debug("Request to get SocialContact : {}", id);
        return socialContactRepository.findById(id)
            .map(socialContactMapper::toDto);
    }

    /**
     * Delete the socialContact by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SocialContact : {}", id);
        socialContactRepository.deleteById(id);
    }
}
