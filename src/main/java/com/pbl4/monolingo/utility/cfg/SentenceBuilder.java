package com.pbl4.monolingo.utility.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SentenceBuilder {

    private List<String> templateSentence;

    private HashMap<String, List<String>> wordMap;

    private List<String> pronouns;
    private List<String> properNouns;
    private List<String> verbs;
    private List<String> objects;

    public SentenceBuilder(List<String> templateSentence) {
        this.templateSentence = templateSentence;

        InitData();
    }

    public void setVerbs(List<String> verbs) {
        this.verbs.addAll(verbs);
    }

    public void setPronouns(List<String> pronouns) {
        this.pronouns.addAll(pronouns);
    }

    public void setProperNouns(List<String> properNouns) {
        this.properNouns.addAll(properNouns);
    }

    public void setObjects(List<String> objects) {
        this.objects.addAll(objects);
    }

    public SentenceResult build() {
        StringBuilder sentence = new StringBuilder();
        StringBuilder translatedSentence = new StringBuilder();

        for (String p : templateSentence) {
            String[] rStrs = randomSelect(wordMap.get(p)).split(",");

            sentence.append(rStrs[0]).append(" ");
            translatedSentence.append(rStrs[1]).append(" ");
        }

        sentence.setCharAt(0, Character.toUpperCase(sentence.charAt(0)));
        translatedSentence.setCharAt(0, Character.toUpperCase(translatedSentence.charAt(0)));
        return new SentenceResult(sentence.toString(), translatedSentence.toString());
    }

    private void InitData() {
        wordMap = new HashMap<>();

        wordMap.put("Pronoun", new ArrayList<>(List.of(
                "I,tôi",
                "you,bạn",
                "we,chúng tôi",
                "they,bọn họ",
                "he,anh",
                "she,cô",
                "it,nó")));

        wordMap.put("PNoun", new ArrayList<>(List.of("Vietnam,Việt Nam")));
        wordMap.put("Verb", new ArrayList<>(List.of("love,yêu", "want,muốn")));
        wordMap.put("O", new ArrayList<>(List.of("cake,bánh", "food,đồ ăn")));
    }

    private String randomSelect(List<String> ls) {
        Random random = new Random();
        int rIndex = random.nextInt(0, ls.size());

        return ls.get(rIndex);
    }
    public List<String> getTemplateSentence() {
        return templateSentence;
    }

}