package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.SynsetRelId;
import jakarta.persistence.*;

@Entity
@Table(name = "similar")
public class Similar {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "synsetId1", column = @Column(name = "synset_id_1")),
            @AttributeOverride(name = "synsetId2", column = @Column(name = "synset_id_2")),
    })
    private SynsetRelId id;

    public Similar(SynsetRelId id) {
        this.id = id;
    }

    public Similar() {}

    public SynsetRelId getId() {
        return id;
    }

    public void setId(SynsetRelId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Similar{" +
                "id=" + id +
                '}';
    }
}
