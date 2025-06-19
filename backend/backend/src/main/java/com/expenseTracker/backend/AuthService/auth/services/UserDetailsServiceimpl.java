package com.expenseTracker.backend.AuthService.auth.services;

import com.expenseTracker.backend.AuthService.auth.entities.UserInfo;
import com.expenseTracker.backend.AuthService.auth.eventProducer.UserInfoEvent;
import com.expenseTracker.backend.AuthService.auth.eventProducer.UserInfoProducer;
import com.expenseTracker.backend.AuthService.auth.models.UserInfoDto;
import com.expenseTracker.backend.AuthService.auth.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class UserDetailsServiceimpl implements UserDetailsService {

   private final UserRepository userRepository;
   private final UserInfoProducer userInfoProducer;


    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceimpl(UserRepository userRepository, UserInfoProducer userInfoProducer, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userInfoProducer = userInfoProducer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User is not found");
        }
        return new CustomUserDetails(userInfo);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDto userInfoDto) {
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signUpUser(UserInfoDto userInfoDto)throws Exception {
        if (validateSignUpDetails(userInfoDto)) {
            userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
            if (Objects.nonNull(checkIfUserAlreadyExists(userInfoDto))) {
                return false;
            }
            String userId = UUID.randomUUID().toString();
            userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));
            userInfoProducer.sendEventToKafka(eventToPublish(userInfoDto,userId));
            return true;
        }
        return false;
    }

    public Boolean validateSignUpDetails(UserInfoDto userInfoDto) throws Exception {
        final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        final Pattern pattern=Pattern.compile(emailRegex);
        if (userInfoDto.getEmail()==null||!pattern.matcher(userInfoDto.getEmail()).matches()){
            throw new Exception("Please provide a valid email");
        }
        if (userInfoDto.getPassword()==null||userInfoDto.getPassword().length()<7){
            throw new Exception("Password length must be greater than 7");
        }
        return true;
    }
    private UserInfoEvent eventToPublish(UserInfoDto userInfoDto,String userId){
        return new UserInfoEvent(userInfoDto.getFirstName(),userInfoDto.getLastName(),userInfoDto.getEmail(),userInfoDto.getPhoneNumber(),userId);
    }
}
