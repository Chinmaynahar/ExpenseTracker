package com.backend.userservice.consumer;


import com.backend.userservice.entities.UserInfoDto;
import com.backend.userservice.repository.UserRepository;
import com.backend.userservice.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


//required for consuming events (Component)
@Component
public class AuthServiceConsumer {
    private UserRepository userRepository;
    private UserService userService;



    @Autowired
    public AuthServiceConsumer(ObjectMapper objectMapper, UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
    @KafkaListener(topics="${spring.kafka.topic-json.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void listener(UserInfoDto eventData){
        try {
            userService.createOrUpdateUser(eventData);
        }catch (Exception e){
            System.out.println("Exception in consumer service");
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
