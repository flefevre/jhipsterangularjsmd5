package com.cea.digitalworld.dwmicroservice2.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cea.digitalworld.dwmicroservice2.domain.Filehash;
import com.cea.digitalworld.dwmicroservice2.domain.*; // for static metamodels
import com.cea.digitalworld.dwmicroservice2.repository.FilehashRepository;
import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashCriteria;

import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashDTO;
import com.cea.digitalworld.dwmicroservice2.service.mapper.FilehashMapper;

/**
 * Service for executing complex queries for Filehash entities in the database.
 * The main input is a {@link FilehashCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FilehashDTO} or a {@link Page} of {@link FilehashDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FilehashQueryService extends QueryService<Filehash> {

    private final Logger log = LoggerFactory.getLogger(FilehashQueryService.class);

    private final FilehashRepository filehashRepository;

    private final FilehashMapper filehashMapper;

    public FilehashQueryService(FilehashRepository filehashRepository, FilehashMapper filehashMapper) {
        this.filehashRepository = filehashRepository;
        this.filehashMapper = filehashMapper;
    }

    /**
     * Return a {@link List} of {@link FilehashDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FilehashDTO> findByCriteria(FilehashCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Filehash> specification = createSpecification(criteria);
        return filehashMapper.toDto(filehashRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FilehashDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FilehashDTO> findByCriteria(FilehashCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Filehash> specification = createSpecification(criteria);
        return filehashRepository.findAll(specification, page)
            .map(filehashMapper::toDto);
    }

    /**
     * Function to convert FilehashCriteria to a {@link Specification}
     */
    private Specification<Filehash> createSpecification(FilehashCriteria criteria) {
        Specification<Filehash> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Filehash_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Filehash_.name));
            }
            if (criteria.getHashOfFile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHashOfFile(), Filehash_.hashOfFile));
            }
        }
        return specification;
    }

}
