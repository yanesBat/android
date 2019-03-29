package com.karman.ebcard.web.rest;
import com.karman.ebcard.service.SocialContactService;
import com.karman.ebcard.web.rest.errors.BadRequestAlertException;
import com.karman.ebcard.web.rest.util.HeaderUtil;
import com.karman.ebcard.web.rest.util.PaginationUtil;
import com.karman.ebcard.service.dto.SocialContactDTO;
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
 * REST controller for managing SocialContact.
 */
@RestController
@RequestMapping("/api")
public class SocialContactResource {

    private final Logger log = LoggerFactory.getLogger(SocialContactResource.class);

    private static final String ENTITY_NAME = "socialContact";

    private final SocialContactService socialContactService;

    public SocialContactResource(SocialContactService socialContactService) {
        this.socialContactService = socialContactService;
    }

    /**
     * POST  /social-contacts : Create a new socialContact.
     *
     * @param socialContactDTO the socialContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialContactDTO, or with status 400 (Bad Request) if the socialContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-contacts")
    public ResponseEntity<SocialContactDTO> createSocialContact(@RequestBody SocialContactDTO socialContactDTO) throws URISyntaxException {
        log.debug("REST request to save SocialContact : {}", socialContactDTO);
        if (socialContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new socialContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialContactDTO result = socialContactService.save(socialContactDTO);
        return ResponseEntity.created(new URI("/api/social-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-contacts : Updates an existing socialContact.
     *
     * @param socialContactDTO the socialContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialContactDTO,
     * or with status 400 (Bad Request) if the socialContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the socialContactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-contacts")
    public ResponseEntity<SocialContactDTO> updateSocialContact(@RequestBody SocialContactDTO socialContactDTO) throws URISyntaxException {
        log.debug("REST request to update SocialContact : {}", socialContactDTO);
        if (socialContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SocialContactDTO result = socialContactService.save(socialContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-contacts : get all the socialContacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of socialContacts in body
     */
    @GetMapping("/social-contacts")
    public ResponseEntity<List<SocialContactDTO>> getAllSocialContacts(Pageable pageable) {
        log.debug("REST request to get a page of SocialContacts");
        Page<SocialContactDTO> page = socialContactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/social-contacts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /social-contacts/:id : get the "id" socialContact.
     *
     * @param id the id of the socialContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/social-contacts/{id}")
    public ResponseEntity<SocialContactDTO> getSocialContact(@PathVariable Long id) {
        log.debug("REST request to get SocialContact : {}", id);
        Optional<SocialContactDTO> socialContactDTO = socialContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialContactDTO);
    }

    /**
     * DELETE  /social-contacts/:id : delete the "id" socialContact.
     *
     * @param id the id of the socialContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/social-contacts/{id}")
    public ResponseEntity<Void> deleteSocialContact(@PathVariable Long id) {
        log.debug("REST request to delete SocialContact : {}", id);
        socialContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
