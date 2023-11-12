package com.pbl4.monolingo.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
    private LocalDateTime birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Boolean gender;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AccountType type;

    public Account() {}

    public Account(Integer accountId, String username, String password, String name, LocalDateTime birthdate, String email, Boolean gender, byte[] profilePicture, Boolean enabled, AccountType type) {
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

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
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

    public void setType(AccountType type) {
        this.type = type;
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
