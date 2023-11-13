package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.VocabularyId;
import jakarta.persistence.*;

@Entity
@Table(name = "vocabulary")
public class Vocabulary {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "levelId.stageId", column = @Column(name = "stage_id")),
            @AttributeOverride(name = "levelId.levelId", column = @Column(name = "level_id")),
            @AttributeOverride(name = "vocabularyNum", column = @Column(name = "vocabulary_num")),
    })
    private VocabularyId id;

    @ManyToOne
    @MapsId("levelId")
    @JoinColumns({
            @JoinColumn(name = "stage_id", referencedColumnName = "stage_id"),
            @JoinColumn(name = "level_id", referencedColumnName = "level_id")
    })
    private Level level;

    @Column(name = "word")
    private String word;

    @Column(name = "type")
    private String type;

    @Column(name = "meaning")
    private String meaning;

    public Vocabulary() {}

    public Vocabulary(VocabularyId id, Level level, String word, String type, String meaning) {
        this.id = id;
        this.level = level;
        this.word = word;
        this.type = type;
        this.meaning = meaning;
    }

    public VocabularyId getId() {
        return id;
    }

    public void setId(VocabularyId id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Vocabulary{" +
                "id=" + id +
                ", level=" + level +
                ", word='" + word + '\'' +
                ", type='" + type + '\'' +
                ", meaning='" + meaning + '\'' +
                '}';
    }
}
