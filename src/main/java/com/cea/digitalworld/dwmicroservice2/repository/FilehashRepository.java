package com.cea.digitalworld.dwmicroservice2.repository;

import com.cea.digitalworld.dwmicroservice2.domain.Filehash;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Filehash entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilehashRepository extends JpaRepository<Filehash, Long>, JpaSpecificationExecutor<Filehash> {

}
