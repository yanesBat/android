package com.karman.ebcard.web.rest;
import com.karman.ebcard.service.UserPropioService;
import com.karman.ebcard.web.rest.errors.BadRequestAlertException;
import com.karman.ebcard.web.rest.util.HeaderUtil;
import com.karman.ebcard.web.rest.util.PaginationUtil;
import com.karman.ebcard.service.dto.UserPropioDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing UserPropio.
 */
@RestController
@RequestMapping("/api")
public class UserPropioResource {

    private final Logger log = LoggerFactory.getLogger(UserPropioResource.class);

    private static final String ENTITY_NAME = "userPropio";

    private final UserPropioService userPropioService;

    public UserPropioResource(UserPropioService userPropioService) {
        this.userPropioService = userPropioService;
    }

    /**
     * POST  /user-propios : Create a new userPropio.
     *
     * @param userPropioDTO the userPropioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPropioDTO, or with status 400 (Bad Request) if the userPropio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-propios")
    public ResponseEntity<UserPropioDTO> createUserPropio(@RequestBody UserPropioDTO userPropioDTO) throws URISyntaxException {
        log.debug("REST request to save UserPropio : {}", userPropioDTO);
        if (userPropioDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPropio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPropioDTO result = userPropioService.save(userPropioDTO);
        return ResponseEntity.created(new URI("/api/user-propios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-propios : Updates an existing userPropio.
     *
     * @param userPropioDTO the userPropioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPropioDTO,
     * or with status 400 (Bad Request) if the userPropioDTO is not valid,
     * or with status 500 (Internal Server Error) if the userPropioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-propios")
    public ResponseEntity<UserPropioDTO> updateUserPropio(@RequestBody UserPropioDTO userPropioDTO) throws URISyntaxException {
        log.debug("REST request to update UserPropio : {}", userPropioDTO);
        if (userPropioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserPropioDTO result = userPropioService.save(userPropioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPropioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-propios : get all the userPropios.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of userPropios in body
     */
    @GetMapping("/user-propios")
    public ResponseEntity<List<UserPropioDTO>> getAllUserPropios(Pageable pageable, @RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("review-is-null".equals(filter)) {
            log.debug("REST request to get all UserPropios where review is null");
            return new ResponseEntity<>(userPropioService.findAllWhereReviewIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of UserPropios");
        Page<UserPropioDTO> page;
        if (eagerload) {
            page = userPropioService.findAllWithEagerRelationships(pageable);
        } else {
            page = userPropioService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/user-propios?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-propios/:id : get the "id" userPropio.
     *
     * @param id the id of the userPropioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPropioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-propios/{id}")
    public ResponseEntity<UserPropioDTO> getUserPropio(@PathVariable Long id) {
        log.debug("REST request to get UserPropio : {}", id);
        Optional<UserPropioDTO> userPropioDTO = userPropioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPropioDTO);
    }

    /**
     * DELETE  /user-propios/:id : delete the "id" userPropio.
     *
     * @param id the id of the userPropioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-propios/{id}")
    public ResponseEntity<Void> deleteUserPropio(@PathVariable Long id) {
        log.debug("REST request to delete UserPropio : {}", id);
        userPropioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
