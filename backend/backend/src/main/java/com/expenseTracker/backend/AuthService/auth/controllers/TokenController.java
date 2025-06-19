package com.expenseTracker.backend.AuthService.auth.controllers;

import com.expenseTracker.backend.AuthService.auth.entities.RefreshToken;
import com.expenseTracker.backend.AuthService.auth.requests.AuthRequestDto;
import com.expenseTracker.backend.AuthService.auth.requests.RefreshTokenRequestDto;
import com.expenseTracker.backend.AuthService.auth.response.JwtResponseDto;
import com.expenseTracker.backend.AuthService.auth.services.JwtService;
import com.expenseTracker.backend.AuthService.auth.services.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TokenController {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public TokenController(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),authRequestDto.getPassword()));
        if (authentication.isAuthenticated()){
            RefreshToken token=refreshTokenService.createRefreshToken(authRequestDto.getUsername());
            return new ResponseEntity(new JwtResponseDto(jwtService.generateToken(authRequestDto.getUsername()),token.getToken()),HttpStatus.OK);
//                    JwtResponseDto.builder().accessToken(jwtService.generateToken(authRequestDto.getUsername())).token(token.getToken()).build()HttpStatus.OK);
        }else{
            return new ResponseEntity("Exception in User Service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return refreshTokenService.findByToken(refreshTokenRequestDto.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken= jwtService.generateToken(userInfo.getUsername());
                    return new JwtResponseDto(accessToken,refreshTokenRequestDto.getToken());
//                            JwtResponseDto.builder()
//                            .accessToken(accessToken)
//                            .token(refreshTokenRequestDto.getToken()).build();
                }).orElseThrow(()->new RuntimeException("Refresh Token does not exists"));
    }
}
