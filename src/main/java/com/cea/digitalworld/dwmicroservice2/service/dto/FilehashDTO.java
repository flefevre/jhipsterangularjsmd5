package com.cea.digitalworld.dwmicroservice2.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Filehash entity.
 */
public class FilehashDTO implements Serializable {

    private Long id;

    private String name;

    private String hashOfFile;

    @Lob
    private byte[] contentOfFile;
    private String contentOfFileContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashOfFile() {
        return hashOfFile;
    }

    public void setHashOfFile(String hashOfFile) {
        this.hashOfFile = hashOfFile;
    }

    public byte[] getContentOfFile() {
        return contentOfFile;
    }

    public void setContentOfFile(byte[] contentOfFile) {
        this.contentOfFile = contentOfFile;
    }

    public String getContentOfFileContentType() {
        return contentOfFileContentType;
    }

    public void setContentOfFileContentType(String contentOfFileContentType) {
        this.contentOfFileContentType = contentOfFileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilehashDTO filehashDTO = (FilehashDTO) o;
        if (filehashDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filehashDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilehashDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hashOfFile='" + getHashOfFile() + "'" +
            ", contentOfFile='" + getContentOfFile() + "'" +
            "}";
    }
}
