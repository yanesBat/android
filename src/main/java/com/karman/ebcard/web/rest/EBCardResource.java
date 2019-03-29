package com.karman.ebcard.web.rest;
import com.karman.ebcard.service.EBCardService;
import com.karman.ebcard.web.rest.errors.BadRequestAlertException;
import com.karman.ebcard.web.rest.util.HeaderUtil;
import com.karman.ebcard.web.rest.util.PaginationUtil;
import com.karman.ebcard.service.dto.EBCardDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EBCard.
 */
@RestController
@RequestMapping("/api")
public class EBCardResource {

    private final Logger log = LoggerFactory.getLogger(EBCardResource.class);

    private static final String ENTITY_NAME = "eBCard";

    private final EBCardService eBCardService;

    public EBCardResource(EBCardService eBCardService) {
        this.eBCardService = eBCardService;
    }

    /**
     * POST  /eb-cards : Create a new eBCard.
     *
     * @param eBCardDTO the eBCardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eBCardDTO, or with status 400 (Bad Request) if the eBCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eb-cards")
    public ResponseEntity<EBCardDTO> createEBCard(@RequestBody EBCardDTO eBCardDTO) throws URISyntaxException {
        log.debug("REST request to save EBCard : {}", eBCardDTO);
        if (eBCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new eBCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EBCardDTO result = eBCardService.save(eBCardDTO);
        return ResponseEntity.created(new URI("/api/eb-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eb-cards : Updates an existing eBCard.
     *
     * @param eBCardDTO the eBCardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eBCardDTO,
     * or with status 400 (Bad Request) if the eBCardDTO is not valid,
     * or with status 500 (Internal Server Error) if the eBCardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eb-cards")
    public ResponseEntity<EBCardDTO> updateEBCard(@RequestBody EBCardDTO eBCardDTO) throws URISyntaxException {
        log.debug("REST request to update EBCard : {}", eBCardDTO);
        if (eBCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EBCardDTO result = eBCardService.save(eBCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eBCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eb-cards : get all the eBCards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eBCards in body
     */
    @GetMapping("/eb-cards")
    public ResponseEntity<List<EBCardDTO>> getAllEBCards(Pageable pageable) {
        log.debug("REST request to get a page of EBCards");
        Page<EBCardDTO> page = eBCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eb-cards");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /eb-cards/:id : get the "id" eBCard.
     *
     * @param id the id of the eBCardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eBCardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/eb-cards/{id}")
    public ResponseEntity<EBCardDTO> getEBCard(@PathVariable Long id) {
        log.debug("REST request to get EBCard : {}", id);
        Optional<EBCardDTO> eBCardDTO = eBCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eBCardDTO);
    }

    /**
     * DELETE  /eb-cards/:id : delete the "id" eBCard.
     *
     * @param id the id of the eBCardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eb-cards/{id}")
    public ResponseEntity<Void> deleteEBCard(@PathVariable Long id) {
        log.debug("REST request to delete EBCard : {}", id);
        eBCardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
