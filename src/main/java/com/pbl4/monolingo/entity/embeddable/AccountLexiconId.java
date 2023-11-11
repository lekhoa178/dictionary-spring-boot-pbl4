package com.pbl4.monolingo.entity.embeddable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountLexiconId that = (AccountLexiconId) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(lexiconId, that.lexiconId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, lexiconId);
    }
}
