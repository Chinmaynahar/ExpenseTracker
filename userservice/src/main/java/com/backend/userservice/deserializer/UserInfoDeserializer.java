package com.backend.userservice.deserializer;

import com.backend.userservice.entities.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Bean;


public class UserInfoDeserializer  implements Deserializer<UserInfoDto> {

    @Override
    public UserInfoDto deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        UserInfoDto user = null;
        try {
            user = mapper.readValue(bytes, UserInfoDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    }

