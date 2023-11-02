package com.pbl4.monolingo.entity;


import com.pbl4.monolingo.entity.embeddable.UserPacketId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_packet_info")
public class UserPacketInfo {


    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "accountId", column = @Column(name = "account_id")),
            @AttributeOverride(name = "packageId", column = @Column(name = "package_id"))
    })
    private UserPacketId id;

    @OneToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("packageId")
    @JoinColumn(name = "package_id")
    private Package packageType;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    public UserPacketInfo() {}

    public UserPacketInfo(UserPacketId id, Account account, Package packageType, LocalDateTime dueDate) {
        this.id = id;
        this.account = account;
        this.packageType = packageType;
        this.dueDate = dueDate;
    }

    public UserPacketId getId() {
        return id;
    }

    public void setId(UserPacketId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Package getPackageType() {
        return packageType;
    }

    public void setPackageType(Package packageType) {
        this.packageType = packageType;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "UserPacketInfo{" +
                "id=" + id +
                ", account=" + account +
                ", packageType=" + packageType +
                ", dueDate=" + dueDate +
                '}';
    }
}
