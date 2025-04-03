package com.ipin.whatsappclone.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.ipin.whatsappclone.dto.response.UserResponse;
import com.ipin.whatsappclone.entity.UserEntity;
import com.ipin.whatsappclone.mapper.UserMapper;
import com.ipin.whatsappclone.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with idp");
        getUserEmail(token).ifPresent(userEmail -> {
            log.info("Synchronizing user having email {}", userEmail);
            // Optional<UserEntity> optUser = userRepository.findByEmail(userEmail);

            UserEntity user = userMapper.fromTokenAttributes(token.getClaims());
            // optUser.ifPresent(value -> user.setId(optUser.get().getId()));

            userRepository.save(user);
        });
    }
    
    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims();

        if (attributes.containsKey("email")) {
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }

    public List<UserResponse> getAllUsersExceptSelf(Authentication connectedUser) {
        return userRepository.findAllUsersExceptSelf(connectedUser.getName())
            .stream()
            .map(userMapper::toUserResponse)
            .toList();
    }
}
