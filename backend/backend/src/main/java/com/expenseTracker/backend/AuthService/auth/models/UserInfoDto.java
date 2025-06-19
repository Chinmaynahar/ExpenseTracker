package com.expenseTracker.backend.AuthService.auth.models;

import com.expenseTracker.backend.AuthService.auth.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.lang.NonNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoDto extends UserInfo {
    @NonNull
    private String firstName;
    @NonNull
    private String userName;
    @NonNull
    private String lastName;
    @NonNull
    private Long phoneNumber;

    public UserInfoDto(String firstName, String userName, String lastName, Long phoneNumber, String email) {
        this.firstName = firstName;
        this.userName = userName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public UserInfoDto(String userName, String lastName, Long phoneNumber, String email) {
        this.userName = userName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
