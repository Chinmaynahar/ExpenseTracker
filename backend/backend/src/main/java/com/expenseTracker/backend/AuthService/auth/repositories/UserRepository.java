package com.expenseTracker.backend.AuthService.auth.repositories;

import com.expenseTracker.backend.AuthService.auth.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo,Long> {
    public UserInfo findByUsername(String username);
}
