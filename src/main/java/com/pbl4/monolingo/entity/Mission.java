package com.pbl4.monolingo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mission")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer point;

    public Mission(String name, String description, Integer point) {
        this.name = name;
        this.description = description;
        this.point = point;
    }

    public Mission() {
    }

    public Mission(Integer id, String name, String description, Integer point) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
