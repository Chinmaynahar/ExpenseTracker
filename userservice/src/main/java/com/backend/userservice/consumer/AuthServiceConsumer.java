package com.backend.userservice.consumer;

import com.backend.userservice.entities.UserInfo;
import com.backend.userservice.entities.UserInfoDto;
import com.backend.userservice.repository.UserRepository;
import com.backend.userservice.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;

public class AuthServiceConsumer {
    private UserRepository userRepository;
    private UserService userService;

    public AuthServiceConsumer(ObjectMapper objectMapper, UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
    @KafkaListener(topics="${spring.kafka.topic-json.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void listener(UserInfoDto eventData){
        try {
            UserInfo userInfo=eventData.transformtoUserInfo();
            userService.createOrUpdateUser(eventData);
        }catch (Exception e){
            System.out.println("Exception in consumer service");
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
