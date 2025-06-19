package com.expenseTracker.backend.AuthService.auth.requests;



public class AuthRequestDto {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String password;
}
