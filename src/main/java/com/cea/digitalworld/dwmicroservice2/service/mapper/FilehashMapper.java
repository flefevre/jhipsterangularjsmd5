package com.cea.digitalworld.dwmicroservice2.service.mapper;

import com.cea.digitalworld.dwmicroservice2.domain.*;
import com.cea.digitalworld.dwmicroservice2.service.dto.FilehashDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Filehash and its DTO FilehashDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FilehashMapper extends EntityMapper<FilehashDTO, Filehash> {



    default Filehash fromId(Long id) {
        if (id == null) {
            return null;
        }
        Filehash filehash = new Filehash();
        filehash.setId(id);
        return filehash;
    }
}
