package com.cea.digitalworld.dwmicroservice2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cea.digitalworld.dwmicroservice2.service.FilehashService;
import com.cea.digitalworld.dwmicroservice2.web.rest.errors.BadRequestAlertException;
import com.cea.digitalworld.dwmicroservice2.web.rest.util.HeaderUtil;
import com.cea.digitalworld.dwmicroservice2.web.rest.util.PaginationUtil;
import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashDTO;
import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashCriteria;
import com.cea.digitalworld.dwmicroservice2.service.FilehashQueryService;
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
 * REST controller for managing Filehash.
 */
@RestController
@RequestMapping("/api")
public class FilehashResource {

    private final Logger log = LoggerFactory.getLogger(FilehashResource.class);

    private static final String ENTITY_NAME = "filehash";

    private final FilehashService filehashService;

    private final FilehashQueryService filehashQueryService;

    public FilehashResource(FilehashService filehashService, FilehashQueryService filehashQueryService) {
        this.filehashService = filehashService;
        this.filehashQueryService = filehashQueryService;
    }

    /**
     * POST  /filehashes : Create a new filehash.
     *
     * @param filehashDTO the filehashDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filehashDTO, or with status 400 (Bad Request) if the filehash has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filehashes")
    @Timed
    public ResponseEntity<FilehashDTO> createFilehash(@RequestBody FilehashDTO filehashDTO) throws URISyntaxException {
        log.debug("REST request to save Filehash : {}", filehashDTO);
        if (filehashDTO.getId() != null) {
            throw new BadRequestAlertException("A new filehash cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilehashDTO result = filehashService.save(filehashDTO);
        return ResponseEntity.created(new URI("/api/filehashes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filehashes : Updates an existing filehash.
     *
     * @param filehashDTO the filehashDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filehashDTO,
     * or with status 400 (Bad Request) if the filehashDTO is not valid,
     * or with status 500 (Internal Server Error) if the filehashDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filehashes")
    @Timed
    public ResponseEntity<FilehashDTO> updateFilehash(@RequestBody FilehashDTO filehashDTO) throws URISyntaxException {
        log.debug("REST request to update Filehash : {}", filehashDTO);
        if (filehashDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FilehashDTO result = filehashService.save(filehashDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filehashDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filehashes : get all the filehashes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of filehashes in body
     */
    @GetMapping("/filehashes")
    @Timed
    public ResponseEntity<List<FilehashDTO>> getAllFilehashes(FilehashCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Filehashes by criteria: {}", criteria);
        Page<FilehashDTO> page = filehashQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filehashes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filehashes/:id : get the "id" filehash.
     *
     * @param id the id of the filehashDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filehashDTO, or with status 404 (Not Found)
     */
    @GetMapping("/filehashes/{id}")
    @Timed
    public ResponseEntity<FilehashDTO> getFilehash(@PathVariable Long id) {
        log.debug("REST request to get Filehash : {}", id);
        Optional<FilehashDTO> filehashDTO = filehashService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filehashDTO);
    }

    /**
     * DELETE  /filehashes/:id : delete the "id" filehash.
     *
     * @param id the id of the filehashDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filehashes/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilehash(@PathVariable Long id) {
        log.debug("REST request to delete Filehash : {}", id);
        filehashService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
