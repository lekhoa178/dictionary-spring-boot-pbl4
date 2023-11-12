package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class LexiconId implements Serializable {

    private BigDecimal synsetId;

    private BigDecimal lexiconNum;


    public LexiconId() {}

    public LexiconId(BigDecimal synsetId, BigDecimal lexiconNum) {
        this.synsetId = synsetId;
        this.lexiconNum = lexiconNum;
    }

    public BigDecimal getSynsetId() {
        return synsetId;
    }

    public void setSynsetId(BigDecimal synsetId) {
        this.synsetId = synsetId;
    }

    public BigDecimal getLexiconNum() {
        return lexiconNum;
    }

    public void setLexiconNum(BigDecimal lexiconNum) {
        this.lexiconNum = lexiconNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LexiconId lexiconId = (LexiconId) o;
        return Objects.equals(synsetId, lexiconId.synsetId) && Objects.equals(lexiconNum, lexiconId.lexiconNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(synsetId, lexiconNum);
    }
}
