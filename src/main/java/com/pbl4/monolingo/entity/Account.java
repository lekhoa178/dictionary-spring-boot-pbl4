package com.pbl4.monolingo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.context.annotation.Primary;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Boolean gender = false;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column
    private Boolean online= false;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "type_id")
    private AccountType type;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private ExtraInformation extraInformation;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<DataPerDay> dataPerDay;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<DailyMission> dailyMissions;

    public Account() {}

    public Account(String username, String password, String name, boolean online) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Account(Integer accountId, String username, String password, String name, Date birthdate, String email, Boolean gender, byte[] profilePicture, Boolean enabled, AccountType type) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
        this.profilePicture = profilePicture;
        this.enabled = enabled;
        this.type = type;
    }

    public Account(String username, String password, AccountType type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public Account(String username, String password, Boolean enabled, AccountType type) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.type = type;
    }

    public Account(String username, String password, String email, Boolean enabled, AccountType type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.type = type;
    }

    public Account(String username, String password, String name, String email, Boolean gender, Boolean enabled, AccountType type) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.enabled = enabled;
        this.type = type;
    }

    public Account(Integer accountId, String username, String name, Date birthdate, String email, Boolean gender) {
        this.accountId = accountId;
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
    }

    public Account(Integer accountId, String username, String name, Date birthdate, String email, Boolean gender, AccountType type) {
        this.accountId = accountId;
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
        this.type = type;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(type.getType()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public AccountType getType() {
        return type;
    }

    public ExtraInformation getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(ExtraInformation extraInformation) {
        this.extraInformation = extraInformation;
    }

    public void setType(AccountType type) {
        this.type = type;
    }


    public Set<DataPerDay> getDataPerDay() {
        return dataPerDay;
    }

    public void setDataPerDay(Set<DataPerDay> dataPerDay) {
        this.dataPerDay = dataPerDay;
    }

    public Set<DailyMission> getDailyMissions() {
        return dailyMissions;
    }

    public void setDailyMissions(Set<DailyMission> dailyMissions) {
        this.dailyMissions = dailyMissions;
    }
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                ", enabled=" + enabled +
                ", type=" + type +
                '}';
    }
}
