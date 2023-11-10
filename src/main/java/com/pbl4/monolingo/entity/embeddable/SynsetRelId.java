package com.pbl4.monolingo.entity.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SynsetRelId implements Serializable {

    private BigDecimal synsetId1;
    private BigDecimal synsetId2;

    public SynsetRelId() {}

    public SynsetRelId(BigDecimal synsetId1, BigDecimal synsetId2) {
        this.synsetId1 = synsetId1;
        this.synsetId2 = synsetId2;
    }

    public BigDecimal getSynsetId1() {
        return synsetId1;
    }

    public void setSynsetId1(BigDecimal synsetId1) {
        this.synsetId1 = synsetId1;
    }

    public BigDecimal getSynsetId2() {
        return synsetId2;
    }

    public void setSynsetId2(BigDecimal synsetId2) {
        this.synsetId2 = synsetId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SynsetRelId that = (SynsetRelId) o;
        return Objects.equals(synsetId1, that.synsetId1) && Objects.equals(synsetId2, that.synsetId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(synsetId1, synsetId2);
    }
}
