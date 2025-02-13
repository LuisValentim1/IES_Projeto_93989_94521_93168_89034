package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    
    
    public User() { super(); }

    public User(String name, String email, Date entryDate, String password, String phoneNumber, Timestamp lastJoined, Boolean isActive) {
        this.name = name;
        this.email = email;
        this.entryDate = entryDate;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.lastJoined = lastJoined;
        this.isActive = isActive;
    }

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "password", nullable = false, length = 64)
    private String password; // TODO é necessário encriptar a password

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "last_joined")
    private Timestamp lastJoined;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "role")
    private String role = "user";

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private Set<Parcel> parcels = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Avaliation> avaliations = new HashSet<Avaliation>();

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
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
    	if (! (obj instanceof User)) {
    		return false;
    	}
    	User other = (User) obj;
    	if (this.getUserId() == other.getUserId()) {
    		return true;
    	}
    	return false;
    	
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Avaliation> getAvaliations() {
        return avaliations;
    }

    public void setAvaliations(Set<Avaliation> avaliations) {
        this.avaliations = avaliations;
    }
}
