package com.pbl4.monolingo.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "account_type")
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role")
    private String type;

    public AccountType() {}

    public AccountType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String name) {
        this.type = name;
    }

    @Override
    public String toString() {
        return "AccountType{" +
                "id=" + id +
                ", name='" + type + '\'' +
                '}';
    }
}
