package com.pbl4.monolingo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "package")
public class Package {

    @Id
    @Column(name = "package_id")
    private Integer packetId;

    @Column(name = "name")
    private String packetName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "duration")
    private Integer duration;

    public Package() {}

    public Package(Integer packetId, String packetName, Integer price, Integer duration) {
        this.packetId = packetId;
        this.packetName = packetName;
        this.price = price;
        this.duration = duration;
    }

    public Integer getPacketId() {
        return packetId;
    }

    public void setPacketId(Integer packetId) {
        this.packetId = packetId;
    }

    public String getPacketName() {
        return packetName;
    }

    public void setPacketName(String packetName) {
        this.packetName = packetName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
