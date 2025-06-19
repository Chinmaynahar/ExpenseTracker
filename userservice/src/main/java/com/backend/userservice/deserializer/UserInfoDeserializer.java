package com.backend.userservice.deserializer;

import com.backend.userservice.entities.UserInfoDto;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import java.io.IOException;


public class UserInfoDeserializer  implements Deserializer<UserInfoDto> {

    @Override
    public UserInfoDto deserialize(String s, byte[] bytes) {
        ObjectMapper objectMapper=new ObjectMapper();
        UserInfoDto user=null;
        try {
            user=objectMapper.readValue(bytes,UserInfoDto.class);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
