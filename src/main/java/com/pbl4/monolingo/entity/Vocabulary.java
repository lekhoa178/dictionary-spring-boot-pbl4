package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.VocabularyId;
import jakarta.persistence.*;

@Entity
@Table(name = "vocabulary")
public class Vocabulary {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "stageId", column = @Column(name = "stage_id")),
            @AttributeOverride(name = "vocabularyNum", column = @Column(name = "vocabulary_num")),
    })
    private VocabularyId id;

    @ManyToOne
    @MapsId("stageId")
    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id")
    private Stage stage;

    @Column(name = "type")
    private String type;

    @Column(name = "meaning")
    private String meaning;

    public Vocabulary() {}

    public Vocabulary(VocabularyId id, Stage stage, String type, String meaning) {
        this.id = id;
        this.stage = stage;
        this.type = type;
        this.meaning = meaning;
    }

    public VocabularyId getId() {
        return id;
    }

    public void setId(VocabularyId id) {
        this.id = id;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
                ", stage=" + stage +
                ", type='" + type + '\'' +
                ", meaning='" + meaning + '\'' +
                '}';
    }
}
