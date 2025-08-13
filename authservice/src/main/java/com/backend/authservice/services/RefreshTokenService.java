package com.backend.authservice.services;


import com.backend.authservice.entities.RefreshToken;
import com.backend.authservice.entities.UserInfo;
import com.backend.authservice.repositories.UserRepository;
import com.backend.authservice.repositories.refreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Service
public class RefreshTokenService {

   private final refreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshTokenService(refreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String username){
        UserInfo userInfo=userRepository.findByUsername(username);
        RefreshToken refreshToken= new RefreshToken(UUID.randomUUID().toString(),Instant.now().plusMillis(60000),userInfo);
//                .builder()
//                .userInfo(userInfo)
//                .token(UUID.randomUUID().toString())
//                .expiryDate(Instant.now().plusMillis(60000))
//                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken()+"Token expired.Please Login Again");
        }
        return token;
    }
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
