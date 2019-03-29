package com.karman.ebcard.service;

import com.karman.ebcard.domain.EBCard;
import com.karman.ebcard.repository.EBCardRepository;
import com.karman.ebcard.service.dto.EBCardDTO;
import com.karman.ebcard.service.mapper.EBCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing EBCard.
 */
@Service
@Transactional
public class EBCardService {

    private final Logger log = LoggerFactory.getLogger(EBCardService.class);

    private final EBCardRepository eBCardRepository;

    private final EBCardMapper eBCardMapper;

    public EBCardService(EBCardRepository eBCardRepository, EBCardMapper eBCardMapper) {
        this.eBCardRepository = eBCardRepository;
        this.eBCardMapper = eBCardMapper;
    }

    /**
     * Save a eBCard.
     *
     * @param eBCardDTO the entity to save
     * @return the persisted entity
     */
    public EBCardDTO save(EBCardDTO eBCardDTO) {
        log.debug("Request to save EBCard : {}", eBCardDTO);
        EBCard eBCard = eBCardMapper.toEntity(eBCardDTO);
        eBCard = eBCardRepository.save(eBCard);
        return eBCardMapper.toDto(eBCard);
    }

    /**
     * Get all the eBCards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EBCardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EBCards");
        return eBCardRepository.findAll(pageable)
            .map(eBCardMapper::toDto);
    }


    /**
     * Get one eBCard by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EBCardDTO> findOne(Long id) {
        log.debug("Request to get EBCard : {}", id);
        return eBCardRepository.findById(id)
            .map(eBCardMapper::toDto);
    }

    /**
     * Delete the eBCard by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EBCard : {}", id);
        eBCardRepository.deleteById(id);
    }
}
