package com.pbl4.monolingo.entity.embeddable;

import java.io.Serializable;
import java.math.BigDecimal;

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
}
