package com.pbl4.monolingo.utility.cfg;

import com.pbl4.monolingo.utility.enplural.English;

import java.util.*;

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

        String verbForm = "";

        for (String p : templateSentence) {
            String[] rStrs = null;
            String keyWord = p;
            boolean pluralVerb = false;

            if (p.contains("/")) {
                String uniqueStr = p.substring(1);
                rStrs = new String[]{ uniqueStr, uniqueStr };
            }
            else if (p.contains("+")) {
                keyWord = p.substring(1);
                pluralVerb = true;
            } else if (p.equals("Be")) {
                rStrs = new String[] { verbForm, "thì" };
                verbForm = "";
            }

            if (rStrs == null) {
                List<String> words = wordMap.get(keyWord);

                if (words == null) {
                    rStrs = p.split(",", -1);
                } else
                    rStrs = randomSelect(words).split(",", -1);


                if (pluralVerb) { // handle plural form of verb
                    if (!verbForm.equals("are")) {
                        verbForm = "";
                        rStrs[0] = English.plural(rStrs[0]);
                    }
                }
            }
            sentence.append(rStrs[0]).append(" ");
            if (!rStrs[1].isEmpty())
                translatedSentence.append(rStrs[1]).append(" ");

            verbForm = getVerbForm(keyWord, rStrs[0]);

        }

        sentence.setCharAt(0, Character.toUpperCase(sentence.charAt(0)));
        translatedSentence.setCharAt(0, Character.toUpperCase(translatedSentence.charAt(0)));
        return new SentenceResult(sentence.toString(), translatedSentence.toString());
    }

    private String getVerbForm(String keyWord, String word) {
        switch (keyWord) {
            case "Nor3":
                return "are";
            case "Pronoun":
                switch (word) {
                    case "I":
                        return "am";
                    case "you":
                    case "we":
                    case "they":
                        return "are";
                    case "he":
                    case "she":
                    case "it":
                        return "is";
                }

            default: return "is";
        }
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

        wordMap.put("Det1", new ArrayList<>(List.of("a,một", "the,", ",")));
        wordMap.put("Det2", new ArrayList<>(List.of("an,một", "the,", ",")));
        wordMap.put("Det3", new ArrayList<>(List.of("the,", ",")));

        wordMap.put("Nor1", new ArrayList<>(List.of("house,ngôi nhà", "spoon,cái muỗng", "knife,cái dao")));
        wordMap.put("Nor2", new ArrayList<>(List.of("apple,quả táo", "umbrella,cái ô")));
        wordMap.put("Nor3", new ArrayList<>(List.of("knives,những cái dao", "spoons,những cái muỗng")));

        wordMap.put("Verb1", new ArrayList<>(List.of("do,làm")));
        wordMap.put("Verb2", new ArrayList<>(List.of("love,yêu", "want,muốn")));
        wordMap.put("O", new ArrayList<>(List.of("cake,bánh", "food,đồ ăn")));

        wordMap.put("Adj", new ArrayList<>(List.of("fast,nhanh", "big,to")));
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