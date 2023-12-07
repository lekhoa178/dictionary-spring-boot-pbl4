package com.pbl4.monolingo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mission")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "point")
    private Integer point;
    @Column(name = "target")
    private Double target;
    @Column(name = "type")
    private Integer type;



    public Mission(String name, String description, Integer point, Double target) {
        this.name = name;
        this.description = description;
        this.point = point;
        this.target = target;
    }

    public Mission() {
    }

    public Mission(Integer id, String name, String description, Integer point, Integer type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.point = point;
        this.type = type;
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

    public Integer getType() {
        return type;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
