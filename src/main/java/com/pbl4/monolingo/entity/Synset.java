package com.pbl4.monolingo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "synset")
public class Synset {

    @Id
    @Column(name = "synset_id")
    private BigDecimal synsetId;

    @Column(name = "definition")
    private String definition;
    public Synset(BigDecimal synsetId, String definition) {
        this.synsetId = synsetId;
        this.definition = definition;
    }

    public Synset() {}

    public void setDefinition(String definition) {
        this.definition = definition;
    }
    public BigDecimal getSynsetId() {
        return this.synsetId;
    }

    public void setSynsetId(BigDecimal synsetId) {
        this.synsetId = synsetId;
    }

    public String getDefinition() {
        return this.definition;
    }

    @Override
    public String toString() {
        return "Synset{" +
                "synsetId=" + synsetId +
                ", definition='" + definition + '\'' +
                '}';
    }
}
