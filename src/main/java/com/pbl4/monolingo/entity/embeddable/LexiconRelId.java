package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LexiconRelId implements Serializable {

    private LexiconId lexiconId1;
    private LexiconId lexiconId2;

    public LexiconRelId() {}

    public LexiconRelId(LexiconId lexiconId1, LexiconId lexiconId2) {
        this.lexiconId1 = lexiconId1;
        this.lexiconId2 = lexiconId2;
    }

    public LexiconId getLexiconId1() {
        return lexiconId1;
    }

    public void setLexiconId1(LexiconId lexiconId1) {
        this.lexiconId1 = lexiconId1;
    }

    public LexiconId getLexiconId2() {
        return lexiconId2;
    }

    public void setLexiconId2(LexiconId lexiconId2) {
        this.lexiconId2 = lexiconId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LexiconRelId that = (LexiconRelId) o;
        return Objects.equals(lexiconId1, that.lexiconId1) && Objects.equals(lexiconId2, that.lexiconId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexiconId1, lexiconId2);
    }
}
