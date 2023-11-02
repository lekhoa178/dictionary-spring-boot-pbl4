package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.LexiconRelId;
import jakarta.persistence.*;

@Entity
@Table(name = "see_also")
public class SeeAlso {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "lexiconId1.lexiconNum", column = @Column(name = "wnum_1")),
            @AttributeOverride(name = "lexiconId1.synsetId", column = @Column(name = "synset_id_1")),
            @AttributeOverride(name = "lexiconId2.lexiconNum", column = @Column(name = "wnum_2")),
            @AttributeOverride(name = "lexiconId2.synsetId", column = @Column(name = "synset_id_2"))
    })
    private LexiconRelId id;

    public SeeAlso() {}

    public SeeAlso(LexiconRelId id) {
        this.id = id;
    }

    public LexiconRelId getId() {
        return id;
    }

    public void setId(LexiconRelId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SeeAlso{" +
                "id=" + id +
                '}';
    }
}
