package com.ipin.whatsappclone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.ipin.whatsappclone.dto.response.ChatResponse;
import com.ipin.whatsappclone.entity.ChatEntity;
import com.ipin.whatsappclone.entity.UserEntity;
import com.ipin.whatsappclone.mapper.ChatMapper;
import com.ipin.whatsappclone.repository.ChatRepository;
import com.ipin.whatsappclone.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChatService {
    
    ChatRepository chatRepository;
    ChatMapper chatMapper;
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(String userId) {
        // final String userId = currentUser.getUsername();
        log.info("Current user ID: {}", userId);
        return chatRepository.findChatBySenderId(userId)
                .stream()
                .map(chat -> chatMapper.toChatResponse(chat, userId))
                .toList();
    }

    public String createChat(String senderId, String receiverId) {
        Optional<ChatEntity> exitingChat = chatRepository.findChatByReceiverAndSender(senderId, receiverId);

        if (exitingChat.isPresent()) {
            return exitingChat.get().getId();
        }

        UserEntity sender = userRepository.findByPublicId(senderId)
            .orElseThrow(() -> new EntityNotFoundException("User with id " + senderId + " not found"));

        UserEntity receiver = userRepository.findByPublicId(receiverId)
            .orElseThrow(() -> new EntityNotFoundException("User with id " + receiverId + " not found"));

        ChatEntity chat = new ChatEntity();
        chat.setSender(sender);
        chat.setRecipient(receiver);

        ChatEntity savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }

}
