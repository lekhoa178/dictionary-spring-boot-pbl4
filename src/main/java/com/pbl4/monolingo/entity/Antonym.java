package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.LexiconRelId;
import jakarta.persistence.*;

@Entity
@Table(name = "antonym")
public class Antonym {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "lexiconId1.lexiconNum", column = @Column(name = "wnum_1")),
            @AttributeOverride(name = "lexiconId1.synsetId", column = @Column(name = "synset_id_1")),
            @AttributeOverride(name = "lexiconId2.lexiconNum", column = @Column(name = "wnum_2")),
            @AttributeOverride(name = "lexiconId2.synsetId", column = @Column(name = "synset_id_2"))
    })
    private LexiconRelId id;

//    @ManyToOne
//    @MapsId("lexiconId1")
//    @JoinColumns({
//            @JoinColumn(name = "wnum_1", referencedColumnName = "lexicon_num"),
//            @JoinColumn(name = "synset_id_1", referencedColumnName = "synset_id")
//    })
//    private Lexicon lexicon1;
//
//    @ManyToOne
//    @MapsId("lexiconId2")
//    @JoinColumns({
//            @JoinColumn(name = "wnum_2", referencedColumnName = "lexicon_num"),
//            @JoinColumn(name = "synset_id_2", referencedColumnName = "synset_id")
//    })
//    private Lexicon lexicon2;
//
//
    public Antonym() {
    }


    public Antonym(LexiconRelId id) {
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
        return "Antonym{" +
                "id=" + id +
                '}';
    }
}
