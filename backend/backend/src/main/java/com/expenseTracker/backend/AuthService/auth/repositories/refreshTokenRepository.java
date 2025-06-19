package com.expenseTracker.backend.AuthService.auth.repositories;

import com.expenseTracker.backend.AuthService.auth.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface refreshTokenRepository extends CrudRepository<RefreshToken,Integer> {

    Optional<RefreshToken> findByToken(String token);
}
