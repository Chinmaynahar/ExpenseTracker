package com.backend.userservice.controller;

import com.backend.userservice.entities.UserInfo;
import com.backend.userservice.entities.UserInfoDto;
import com.backend.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/v1/createUpdate")
    public ResponseEntity<UserInfoDto> createUpdate(@RequestBody UserInfoDto userInfoDto){
        try {
        UserInfoDto user=userService.getUser(userInfoDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
