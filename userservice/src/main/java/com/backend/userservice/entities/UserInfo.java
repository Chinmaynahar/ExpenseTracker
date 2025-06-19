package com.backend.userservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;
@Entity
@Table(name = "users")
public class UserInfo {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Id
    @NonNull
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("first_name")
    @NonNull
    private String firstName;
    @JsonProperty("last_name")
    @NonNull
    private String lastName;
    @JsonProperty("phone_number")
    @NonNull
    private Long phoneNumber;
    @JsonProperty("email")
    @NonNull
    private String email;
    @JsonProperty("profile_pic")
    @NonNull
    private String profilePic;

    public UserInfo() {
    }

    public UserInfo(@NonNull String userId, @NonNull String firstName, @NonNull String lastName, @NonNull Long phoneNumber, @NonNull String email, @NonNull String profilePic) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.profilePic = profilePic;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(@NonNull String profilePic) {
        this.profilePic = profilePic;
    }
}
