package com.pbl4.monolingo.utility.cfg;

public class SentenceResult {
    private String sentence;
    private String translatedSentence;

    public SentenceResult(String sentence, String translatedSentence) {
        this.sentence = sentence;
        this.translatedSentence = translatedSentence;
    }

    public String getSentence() {
        return sentence;
    }

    public String getTranslatedSentence() {
        return translatedSentence;
    }

    @Override
    public String toString() {
        return sentence + "-----" + translatedSentence;
    }
}