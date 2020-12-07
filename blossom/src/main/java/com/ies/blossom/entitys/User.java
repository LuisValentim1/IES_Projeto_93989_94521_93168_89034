package com.ies.blossom.entitys;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "password")
    private String password; // TODO é necessário encriptar a password

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "last_joined")
    private Timestamp lastJoined;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    private Set<Parcel> parcels;

    public User() {}

    public User(String name, String email, Date entryDate, String password, String phoneNumber, Timestamp lastJoined, Boolean isActive) {
        this.name = name;
        this.email = email;
        this.entryDate = entryDate;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.lastJoined = lastJoined;
        this.isActive = isActive;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getLastJoined() {
        return lastJoined;
    }

    public void setLastJoined(Timestamp lastJoined) {
        this.lastJoined = lastJoined;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(Set<Parcel> parcels) {
        this.parcels = parcels;
    }
}
