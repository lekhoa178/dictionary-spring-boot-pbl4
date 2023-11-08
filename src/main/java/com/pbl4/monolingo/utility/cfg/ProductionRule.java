package com.pbl4.monolingo.utility.cfg;

import java.util.List;

public class ProductionRule {

    private String lhs; // Left-hand side (non-terminal)
    private List<String> rhs; // Right-hand side (list of symbols)

    public ProductionRule(String lhs, List<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getLHS() {
        return lhs;
    }

    public List<String> getRHS() {
        return rhs;
    }

    public void addRHS(List<String> addLhs) {
        rhs.addAll(addLhs);
    }

    @Override
    public String toString() {
        return lhs + " -> " + String.join(" ", rhs);
    }

}
