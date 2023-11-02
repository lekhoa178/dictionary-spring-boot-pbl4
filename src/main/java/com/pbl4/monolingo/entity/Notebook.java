package com.pbl4.monolingo.entity;

import com.pbl4.monolingo.entity.embeddable.AccountLexiconId;
import jakarta.persistence.*;

@Entity
@Table(name = "notebook")
public class Notebook {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "accountId", column = @Column(name = "account_id")),
            @AttributeOverride(name = "lexiconId.lexiconNum", column = @Column(name = "lexicon_num")),
            @AttributeOverride(name = "lexiconId.synsetId", column = @Column(name = "synset_id"))
    })
    private AccountLexiconId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("lexiconId")
    @JoinColumns({
            @JoinColumn(name = "lexicon_num", referencedColumnName = "lexicon_num"),
            @JoinColumn(name = "synset_id", referencedColumnName = "synset_id")
    })
    private Lexicon lexicon;


    public Notebook() {}

    public Notebook(AccountLexiconId id, Account account, Lexicon lexicon) {
        this.id = id;
        this.account = account;
        this.lexicon = lexicon;
    }

    public AccountLexiconId getId() {
        return id;
    }

    public void setId(AccountLexiconId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Lexicon getLexicon() {
        return lexicon;
    }

    public void setLexicon(Lexicon lexicon) {
        this.lexicon = lexicon;
    }

    @Override
    public String toString() {
        return "Notebook{" +
                "id=" + id +
                ", account=" + account +
                ", lexicon=" + lexicon +
                '}';
    }
}
