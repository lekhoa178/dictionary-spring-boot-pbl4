package com.pbl4.monolingo.utility.uimodel;

import java.util.List;

public class DefinitionDetailView {

    private String definition;
    private List<String> examples;
    private List<String> synonyms;
    private List<String> antonyms;

    public DefinitionDetailView(String definition, List<String> examples, List<String> synonyms, List<String> antonyms) {
        this.definition = definition;
        this.examples = examples;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }
}
