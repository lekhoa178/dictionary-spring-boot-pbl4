package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AccountLexiconId implements Serializable {

    private Integer accountId;

    private LexiconId lexiconId;

    public AccountLexiconId() {}

    public AccountLexiconId(Integer accountId, LexiconId lexiconId) {
        this.accountId = accountId;
        this.lexiconId = lexiconId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public LexiconId getLexiconId() {
        return lexiconId;
    }

    public void setLexiconId(LexiconId lexiconId) {
        this.lexiconId = lexiconId;
    }
}
