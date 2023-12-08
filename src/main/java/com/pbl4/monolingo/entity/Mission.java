package com.pbl4.monolingo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "mission")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "Không được để trống mục này")
    @Size(min=1, message = "Không được để trống mục này")
    private String name;
    @Column(name = "description")
    @NotNull(message = "Không được để trống mục này")
    @Size(min = 1, message = "Không được để trống mục này")
    private String description;
    @Column(name = "point")
    @NotNull(message = "Không được để trống mục này")
    private Integer point;
    @Column(name = "target")
    @NotNull(message = "Không được để trống mục này")
    private Double target;
    @Column(name = "type")
    @NotNull(message = "Không được để trống mục này")
    private Integer type;

    public Mission(String name, String description, Integer point, Double target) {
        this.name = name;
        this.description = description;
        this.point = point;
        this.target = target;
    }

    public Mission() {}

    public Mission(Integer id, String name, String description, Integer point, Double target, Integer type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.point = point;
        this.target = target;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", point=" + point +
                ", target=" + target +
                ", type=" + type +
                '}';
    }

    
}
