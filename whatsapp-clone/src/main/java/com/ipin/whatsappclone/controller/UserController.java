package com.ipin.whatsappclone.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ipin.whatsappclone.dto.response.UserResponse;
import com.ipin.whatsappclone.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User")
@RequiredArgsConstructor
public class UserController {
    UserService userService; 
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.getAllUsersExceptSelf(authentication));
    }
    
}
