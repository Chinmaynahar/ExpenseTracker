package com.backend.userservice.services;

import com.backend.userservice.entities.UserInfo;
import com.backend.userservice.entities.UserInfoDto;
import com.backend.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserInfo createOrUpdateUser(UserInfoDto userInfoDto){
    Function<UserInfo,UserInfo>updateUser=userInfo -> {
        userInfo.setUserId(userInfoDto.getUserId());
        userInfo.setEmail(userInfoDto.getEmail());
        userInfo.setFirstName(userInfoDto.getFirstName());
        userInfo.setLastName(userInfoDto.getLastName());
        userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
        userInfo.setProfilePic(userInfoDto.getProfilePic());
        return userRepository.save(userInfo);
    };
    Supplier<UserInfo>createUser=()->{
          return userRepository.save(userInfoDto.transformtoUserInfo());
    };
    UserInfo userInfo=userRepository.findByUserId(userInfoDto.getUserId())
            .map(updateUser)
            .orElseGet(createUser);
        return new UserInfo(userInfoDto.getUserId(),userInfoDto.getFirstName(),userInfoDto.getLastName(),userInfoDto.getPhoneNumber(),userInfoDto.getEmail(),userInfoDto.getProfilePic());
    }
    public UserInfoDto getUser(UserInfoDto userInfoDto) throws Exception {
        Optional<UserInfo> userInfo=userRepository.findByUserId(userInfoDto.getUserId());
        if (userInfo.isEmpty()){
            throw new Exception("User not found");
        }
        UserInfo userInfo1=userInfo.get();
        return new UserInfoDto(userInfoDto.getUserId(),userInfoDto.getFirstName(),userInfoDto.getLastName(),userInfoDto.getPhoneNumber(),userInfoDto.getEmail(),userInfoDto.getProfilePic());
    }
}
