package com.pbl4.monolingo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pbl4.monolingo.entity.embeddable.LexiconId;
import jakarta.persistence.*;

@Entity
@Table(name = "lexicon")
public class Lexicon {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "lexiconNum", column = @Column(name = "lexicon_num")),
            @AttributeOverride(name = "synsetId", column = @Column(name = "synset_id"))
    })
    private LexiconId id;

    @ManyToOne
    @MapsId("synsetId")
    @JoinColumn(name = "synset_id")
    private Synset synset;

    @Column(name = "word")
    private String word;

    public Lexicon() {}

    public Lexicon(LexiconId id, Synset synset, String word) {
        this.id = id;
        this.synset = synset;
        this.word = word;
    }

    public LexiconId getId() {
        return id;
    }

    public void setId(LexiconId id) {
        this.id = id;
    }

    public Synset getSynset() {
        return synset;
    }

    public void setSynset(Synset synset) {
        this.synset = synset;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Lexicon{" +
                "id=" + id +
                ", word='" + word + '\'' +
                '}';
    }
}
