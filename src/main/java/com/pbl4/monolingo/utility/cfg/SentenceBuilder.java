package com.pbl4.monolingo.utility.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SentenceBuilder {

    private List<String> templateSentence;
    private List<String> translatedTempSentence;

    private HashMap<String, List<String>> wordMap;

    public SentenceBuilder(List<String> templateSentence) {
        this.templateSentence = templateSentence;
        this.translatedTempSentence = new ArrayList<>();

//        for (String s : templateSentence) {
//            if (s.equals("Ad"))
//        }

        initData();
    }

    public void setVerbs(List<String> verbs) {
        setWords("Verb", verbs);
    }

    public void setPronouns(List<String> pronouns) {
        setWords("Pronoun", pronouns);
    }

    public void setPeople(List<String> people) {
        setWords("People", people);
    }

    public void setPlace(List<String> place) {
        setWords("Place", place);
    }

    public SentenceResult build() {
        StringBuilder sentence = new StringBuilder();
        StringBuilder translatedSentence = new StringBuilder();

        for (String p : templateSentence) {
            List<String> words = wordMap.get(p);

            String[] rStrs;
            if (words == null) {
                rStrs = p.split(",");
            }
            else
                rStrs = randomSelect(words).split(",");

            sentence.append(rStrs[0]).append(" ");
            translatedSentence.append(rStrs[1]).append(" ");
        }

        sentence.setCharAt(0, Character.toUpperCase(sentence.charAt(0)));
        translatedSentence.setCharAt(0, Character.toUpperCase(translatedSentence.charAt(0)));
        return new SentenceResult(sentence.toString(), translatedSentence.toString());
    }

    private void setWords(String key, List<String> words) {
        List<String> ws = wordMap.get(key);
        if (words != null) {
            ws.addAll(words);
        }

        wordMap.put(key, ws);
    }

    private void initData() {
        wordMap = new HashMap<>();

        wordMap.put("Pronoun", new ArrayList<>(List.of(
                "I,tôi",
                "you,bạn",
                "we,chúng tôi",
                "they,bọn họ",
                "he,anh ấy",
                "she,cô ấy",
                "it,nó")));

        wordMap.put("People", new ArrayList<>(List.of("Tom,Tom", "Alex,Alex", "Lạc,Lạc")));
        wordMap.put("Place", new ArrayList<>(List.of("Tom's house,nhà của Tom", "Vietnam,Việt Nam")));

        wordMap.put("Det1", new ArrayList<>(List.of("a,một", "the, ", " , ")));
        wordMap.put("Det2", new ArrayList<>(List.of("an,một", "the, ", " , ")));
        wordMap.put("Det3", new ArrayList<>(List.of("the, ", " , ")));

        wordMap.put("Nor1", new ArrayList<>(List.of("house,ngôi nhà", "spoon,cái muỗng", "knife,cái dao")));
        wordMap.put("Nor2", new ArrayList<>(List.of("apple,quả táo", "umbrella,cái ô")));
        wordMap.put("Nor3", new ArrayList<>(List.of("knives,những cái dao", "spoons,những cái muỗng")));

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