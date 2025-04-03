package com.ipin.whatsappclone.mapper;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ipin.whatsappclone.dto.response.UserResponse;
import com.ipin.whatsappclone.entity.UserEntity;

@Service
public class UserMapper {

    public UserEntity fromTokenAttributes(Map<String, Object> attributes) {
        UserEntity user = new UserEntity();

        if (attributes.containsKey("sub")) {
            user.setId(attributes.get("sub").toString());
        }

        if (attributes.containsKey("given_name")) {
            user.setFirstName(attributes.get("given_name").toString());
        } 
        else if (attributes.containsKey("nickname")) {
            user.setFirstName(attributes.get("nickname").toString());
        }

        if (attributes.containsKey("family_name")) {
            user.setLastName(attributes.get("family_name").toString());
        }

        if (attributes.containsKey("email")) {
            user.setEmail(attributes.get("email").toString());
        }

        user.setLastSeen(LocalDateTime.now());
        return user;
    }

    public UserResponse toUserResponse(UserEntity user) {
        return UserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .lastSeen(user.getLastSeen())
            .isOnline(user.isUserOnline())
            .build();
    }
}
