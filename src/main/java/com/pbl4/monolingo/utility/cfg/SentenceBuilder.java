package com.pbl4.monolingo.utility.cfg;

import simplenlg.features.Feature;
import simplenlg.features.InterrogativeType;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

import java.util.*;

public class SentenceBuilder {

    private List<String> templateSentence;
    private List<String> translatedTempSentence;

    private HashMap<String, List<String>> wordMap;

    Lexicon lexicon = Lexicon.getDefaultLexicon();
    NLGFactory nlgFactory = new NLGFactory(lexicon);
    Realiser realiser = new Realiser(lexicon);


    private boolean isNegated = false;
    private boolean isYesNo = false;

    public SentenceBuilder(List<String> templateSentence) {
        this.templateSentence = templateSentence;
        this.translatedTempSentence = new ArrayList<>();

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

    public SentenceResult build() {
        StringBuilder sentence = new StringBuilder();
        StringBuilder translatedSentence = new StringBuilder();

        CoordinatedPhraseElement coordSentences = new CoordinatedPhraseElement();
        SPhraseSpec curSentence = nlgFactory.createClause();

        isNegated = new Random().nextBoolean();

        isYesNo = false;

        int index = -1;
        for (String p : templateSentence) {
            index++;
            String[] rStrs = {"", ""};

            if (p.contains("/")) { // inline words
                String uniqueStr = p.substring(1);
                rStrs = new String[]{ uniqueStr, uniqueStr };
            } else {
                List<String> words = wordMap.get(p);

                if (words == null) {
                    if (p.equals("be")) {
                        VPPhraseSpec vp = generateVerbPhrase("be");
                        curSentence.setVerb(vp);
                        rStrs[1] = "thì";
                    } else if (p.equals("yn")) {
                        curSentence.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);
                        rStrs[1] = "có phải";
                        isYesNo = true;
                    } else rStrs = p.split(",", -1);
                } else {
                    rStrs = randomSelect(words).split(",", -1);

                    if (AnalyzeWord(p, rStrs, curSentence, coordSentences))
                        curSentence = nlgFactory.createClause();
                }

            }

            translatedSentence.append(rStrs[1]).append(" ");
            if (index == templateSentence.size() - 1) {
                coordSentences.addCoordinate(curSentence);
            }

        }

        System.out.println(realiser.realiseSentence(coordSentences));
        sentence.append(realiser.realiseSentence(coordSentences));
        translatedSentence.deleteCharAt(translatedSentence.length() - 1);
        if (isYesNo) translatedSentence.append(" không");


        sentence.setCharAt(sentence.length() - 1, isYesNo ? '?' : '.');
        sentence.setCharAt(0, Character.toUpperCase(sentence.charAt(0)));
        translatedSentence.setCharAt(0, Character.toUpperCase(translatedSentence.charAt(0)));
        return new SentenceResult(sentence.toString(), translatedSentence.toString());
    }

    private boolean AnalyzeWord(String p,
                                String[] rStrs,
                                SPhraseSpec curSentence,
                                CoordinatedPhraseElement coordSentences) {
        switch (p) {
            case "Pronoun":
            case "People":
                NPPhraseSpec np1 = nlgFactory.createNounPhrase(rStrs[0]);
                curSentence.setSubject(np1);

                break;
            case "Nor":
            case "O":
                NPPhraseSpec np2 = generateNounPhrase(rStrs[0]);
                if (p.equals("Nor"))
                    curSentence.setSubject(np2);
                else
                    curSentence.setObject(np2);

                rStrs[1] =
                        np2.getFeatureAsString("postMeaning")
                                + rStrs[1]
                                + np2.getFeatureAsString("afterMeaning");
                break;

            case "Verb1":
            case "Verb2":
                VPPhraseSpec vp = generateVerbPhrase(rStrs[0]);
                vp.setFeature(Feature.NEGATED, isNegated);
                curSentence.setVerb(vp);

                if (isNegated) rStrs[1] = "không " + rStrs[1];
                break;

            case "Conj":
                coordSentences.addCoordinate(curSentence);
                coordSentences.setConjunction(rStrs[0]);

                isNegated = new Random().nextBoolean();

                return true;

            case "AdjVerb":
                VPPhraseSpec vpj = (VPPhraseSpec) curSentence.getVerbPhrase();
                vpj.addModifier(rStrs[0]);

                break;
        }

        return false;
    }

    private NPPhraseSpec generateNounPhrase(String noun) {
        NPPhraseSpec np = nlgFactory.createNounPhrase(noun);

        Random random = new Random();
        boolean isDemonstratives = random.nextBoolean();
        boolean isPlural = random.nextBoolean();
        boolean isThe = random.nextBoolean();

        if (isPlural) { // plural form
            np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

            np.setFeature("postMeaning", "những ");
            np.setFeature("afterMeaning", "");


            if (isDemonstratives) {
                String demons = randomSelect(wordMap.get("DemonsPlural"));
                np.setSpecifier(demons);

                if (demons.equals("these"))
                    np.setFeature("afterMeaning", " này");
                else
                    np.setFeature("afterMeaning", " kia");
            } else {
                if (isThe) np.setDeterminer("the");
            }

        } else { // singular form
            np.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);

            np.setFeature("postMeaning", "");
            np.setFeature("afterMeaning", "");

            if (isDemonstratives) {
                String demons = randomSelect(wordMap.get("DemonsSingular"));
                np.setSpecifier(demons);

                if (demons.equals("this"))
                    np.setFeature("afterMeaning", " này");
                else
                    np.setFeature("afterMeaning", " kia");
            } else {
                if (isThe) {
                    np.setDeterminer("the");
                }
                else {
                    if (isVowelNoun(noun))
                        np.setDeterminer("an");
                    else np.setDeterminer("a");
                }
            }
        }

        return np;
    }

    private VPPhraseSpec generateVerbPhrase(String verb) {
        return nlgFactory.createVerbPhrase(verb);
    }

    private boolean isDemonstratives(String word) {
        return word.equals("this") || word.equals("that") || word.equals("these") || word.equals("those");
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

        wordMap.put("Nor", new ArrayList<>(List.of(
                "house,ngôi nhà", "spoon,cái muỗng", "knife,cái dao",
                "apple,quả táo", "umbrella,cái ô")));

        wordMap.put("Verb1", new ArrayList<>(List.of("do,làm")));
        wordMap.put("Verb2", new ArrayList<>(List.of("love,yêu", "want,muốn")));
        wordMap.put("O", new ArrayList<>(List.of("cake,bánh", "food,đồ ăn")));

        wordMap.put("AdjVerb", new ArrayList<>(List.of(
                "beautiful,đẹp",
                "small,nhỏ",
                "big,to",
                "huge,khổng lồ",
                "black,có màu đen",
                "gorgeous,tuyệt đẹp",
                "useful,tiện lợi")));

        wordMap.put("Conj", new ArrayList<>(List.of("and,và", "or,hoặc")));

        // Support Map
        wordMap.put("DemonsPlural", new ArrayList<>(List.of(
                "these",
                "those")));

        wordMap.put("DemonsSingular", new ArrayList<>(List.of(
                "this",
                "that")));
    }

    private boolean isVowelNoun(String noun) {
        char firstChar = noun.charAt(0);
        return switch (firstChar) {
            case 'e', 'u', 'o', 'a', 'i' -> true;
            default -> false;
        };
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