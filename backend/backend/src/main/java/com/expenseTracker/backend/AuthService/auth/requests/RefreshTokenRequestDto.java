package com.expenseTracker.backend.AuthService.auth.requests;


public class RefreshTokenRequestDto {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RefreshTokenRequestDto(String token) {
        this.token = token;
    }
}
