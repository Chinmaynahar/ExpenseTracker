package com.backend.authservice.repositories;


import com.backend.authservice.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface refreshTokenRepository extends CrudRepository<RefreshToken,Integer> {

    Optional<RefreshToken> findByToken(String token);
}
