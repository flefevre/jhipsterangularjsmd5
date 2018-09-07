package com.cea.digitalworld.dwmicroservice2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Filehash.
 */
@Entity
@Table(name = "filehash")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Filehash implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hash_of_file")
    private String hashOfFile;

    @Lob
    @Column(name = "content_of_file")
    private byte[] contentOfFile;

    @Column(name = "content_of_file_content_type")
    private String contentOfFileContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Filehash name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashOfFile() {
        return hashOfFile;
    }

    public Filehash hashOfFile(String hashOfFile) {
        this.hashOfFile = hashOfFile;
        return this;
    }

    public void setHashOfFile(String hashOfFile) {
        this.hashOfFile = hashOfFile;
    }

    public byte[] getContentOfFile() {
        return contentOfFile;
    }

    public Filehash contentOfFile(byte[] contentOfFile) {
        this.contentOfFile = contentOfFile;
        return this;
    }

    public void setContentOfFile(byte[] contentOfFile) {
        this.contentOfFile = contentOfFile;
    }

    public String getContentOfFileContentType() {
        return contentOfFileContentType;
    }

    public Filehash contentOfFileContentType(String contentOfFileContentType) {
        this.contentOfFileContentType = contentOfFileContentType;
        return this;
    }

    public void setContentOfFileContentType(String contentOfFileContentType) {
        this.contentOfFileContentType = contentOfFileContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Filehash filehash = (Filehash) o;
        if (filehash.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filehash.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filehash{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hashOfFile='" + getHashOfFile() + "'" +
            ", contentOfFile='" + getContentOfFile() + "'" +
            ", contentOfFileContentType='" + getContentOfFileContentType() + "'" +
            "}";
    }
}
