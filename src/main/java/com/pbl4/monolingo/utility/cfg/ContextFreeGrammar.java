package com.pbl4.monolingo.utility.cfg;

import java.util.*;

public class ContextFreeGrammar {
    private ArrayList<ProductionRule> productionRules;

    public ContextFreeGrammar() {
        this.productionRules = new ArrayList<>();
    }

    public void addProductionRule(ProductionRule rule) {
        ProductionRule found = getRuleByLhs(rule.getLHS());

        if (found != null) {
            found.addRHS(rule.getRHS());
        }
        else
            productionRules.add(rule);
    }

    public List<ProductionRule> getProductionRules() {
        return productionRules;
    }

    public SentenceBuilder getSentenceBuilder() {

        ProductionRule initRule = getRuleByLhs("S");
        if (initRule == null) return null;
        List<String> sentence = new ArrayList<>(initRule.getRHS());

        Random random = new Random();
        while (true) {
            List<String> newSentences = new ArrayList<>();
            for (String l : sentence) {

                ProductionRule rule = getRuleByLhs(l);

                if (rule == null) {
                    continue;
                }

                int rIndex = random.nextInt(0, rule.getRHS().size());
                newSentences.addAll(List.of(rule.getRHS().get(rIndex).split(" ")));
            }

            if (newSentences.isEmpty()) break;
            sentence = newSentences;
        }

        return new SentenceBuilder(sentence);
    }

    public ProductionRule getRuleByLhs(String lhs) {
        Optional<ProductionRule> found = productionRules.stream().filter(e -> e.getLHS().equals(lhs)).findFirst();

        return found.orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ProductionRule rule : productionRules) {
            sb.append(rule).append("\n");
        }
        return sb.toString();
    }

}
