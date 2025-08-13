package com.backend.authservice.serializer;


import com.backend.authservice.eventProducer.UserInfoEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class UserInfoSerializer implements Serializer<UserInfoEvent> {
    @Override
    public byte[] serialize(String s, UserInfoEvent userInfoEvent) {
        byte [] retVal=null;
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            retVal=objectMapper.writeValueAsString(userInfoEvent).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return retVal;
    }
}
