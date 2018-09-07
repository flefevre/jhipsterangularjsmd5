package com.cea.digitalworld.dwmicroservice2.service.impl;

import com.cea.digitalworld.dwmicroservice2.service.FilehashService;
import com.cea.digitalworld.dwmicroservice2.domain.Filehash;
import com.cea.digitalworld.dwmicroservice2.repository.FilehashRepository;
import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashDTO;
import com.cea.digitalworld.dwmicroservice2.service.mapper.FilehashMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Filehash.
 */
@Service
@Transactional
public class FilehashServiceImpl implements FilehashService {

    private final Logger log = LoggerFactory.getLogger(FilehashServiceImpl.class);

    private final FilehashRepository filehashRepository;

    private final FilehashMapper filehashMapper;

    public FilehashServiceImpl(FilehashRepository filehashRepository, FilehashMapper filehashMapper) {
        this.filehashRepository = filehashRepository;
        this.filehashMapper = filehashMapper;
    }

    /**
     * Save a filehash.
     *
     * @param filehashDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FilehashDTO save(FilehashDTO filehashDTO) {
        log.debug("Request to save Filehash : {}", filehashDTO);
        Filehash filehash = filehashMapper.toEntity(filehashDTO);
        filehash = filehashRepository.save(filehash);
        return filehashMapper.toDto(filehash);
    }

    /**
     * Get all the filehashes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FilehashDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Filehashes");
        return filehashRepository.findAll(pageable)
            .map(filehashMapper::toDto);
    }


    /**
     * Get one filehash by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FilehashDTO> findOne(Long id) {
        log.debug("Request to get Filehash : {}", id);
        return filehashRepository.findById(id)
            .map(filehashMapper::toDto);
    }

    /**
     * Delete the filehash by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Filehash : {}", id);
        filehashRepository.deleteById(id);
    }
}
