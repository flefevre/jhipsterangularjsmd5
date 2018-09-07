package com.cea.digitalworld.dwmicroservice2.service;

import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Filehash.
 */
public interface FilehashService {

    /**
     * Save a filehash.
     *
     * @param filehashDTO the entity to save
     * @return the persisted entity
     */
    FilehashDTO save(FilehashDTO filehashDTO);

    /**
     * Get all the filehashes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FilehashDTO> findAll(Pageable pageable);


    /**
     * Get the "id" filehash.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FilehashDTO> findOne(Long id);

    /**
     * Delete the "id" filehash.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
