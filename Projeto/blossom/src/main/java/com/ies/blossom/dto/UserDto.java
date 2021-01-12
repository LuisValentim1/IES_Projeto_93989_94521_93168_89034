package com.ies.blossom.dto;

public class UserDto {
    
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public UserDto() {}

    public UserDto(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return "UserDto [email=" + email + ", name=" + name + ", password=" + password + ", phoneNumber=" + phoneNumber
                + "]";
    }

    
}
