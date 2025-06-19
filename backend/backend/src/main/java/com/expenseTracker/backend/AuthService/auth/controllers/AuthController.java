package com.expenseTracker.backend.AuthService.auth.controllers;

import com.expenseTracker.backend.AuthService.auth.entities.RefreshToken;
import com.expenseTracker.backend.AuthService.auth.models.UserInfoDto;
import com.expenseTracker.backend.AuthService.auth.response.JwtResponseDto;
import com.expenseTracker.backend.AuthService.auth.services.JwtService;
import com.expenseTracker.backend.AuthService.auth.services.RefreshTokenService;
import com.expenseTracker.backend.AuthService.auth.services.UserDetailsServiceimpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceimpl userDetailsServiceimpl;
    public AuthController(JwtService jwtService, RefreshTokenService refreshTokenService, UserDetailsServiceimpl userDetailsServiceimpl) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsServiceimpl = userDetailsServiceimpl;
    }
    @PostMapping("auth/signUp")
    public ResponseEntity SignUp(@RequestBody UserInfoDto userInfoDto){
        try {
            Boolean isSignUped=userDetailsServiceimpl.signUpUser(userInfoDto);
            if (Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity("User Already Exists", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken= jwtService.generateToken(userInfoDto.getUsername());
            return new ResponseEntity(new JwtResponseDto(jwtToken,refreshToken.getToken()),HttpStatus.OK);
// JwtResponseDto.builder().accessToken(jwtToken).token(refreshToken.getToken()).build()HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}