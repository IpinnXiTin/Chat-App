package com.ipin.whatsappclone.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ipin.whatsappclone.constants.MessageState;
import com.ipin.whatsappclone.constants.MessageType;
import com.ipin.whatsappclone.constants.NotificationType;
import com.ipin.whatsappclone.dto.request.MessageRequest;
import com.ipin.whatsappclone.dto.response.MessageResponse;
import com.ipin.whatsappclone.dto.response.Notification;
import com.ipin.whatsappclone.entity.ChatEntity;
import com.ipin.whatsappclone.entity.MessageEntity;
import com.ipin.whatsappclone.mapper.MessageMapper;
import com.ipin.whatsappclone.repository.ChatRepository;
import com.ipin.whatsappclone.repository.MessageRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {
    MessageRepository messageRepository;
    ChatRepository chatRepository;
    MessageMapper messageMapper;
    FileService fileService;
    NotificationService notificationService;

    public void saveMessage(MessageRequest messageRequest) {
        ChatEntity chat = chatRepository.findById(messageRequest.getChatId())
            .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        MessageEntity message = new MessageEntity();
        message.setContent(messageRequest.getContent());
        message.setChat(chat);
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getReceiverId());
        message.setType(messageRequest.getType());
        message.setState(MessageState.SENT);
        
        messageRepository.save(message);

        Notification notification = Notification.builder()
            .chatId(chat.getId())
            .messageType(messageRequest.getType())
            .content(messageRequest.getContent())
            .senderId(messageRequest.getSenderId())
            .receiverId(messageRequest.getReceiverId())
            .type(NotificationType.MESSAGE)
            .chatName(chat.getChatName(message.getSenderId()))
            .build();

        notificationService.sendNotification(message.getReceiverId(), notification);
    }

    public List<MessageResponse> findChatMessages(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
            .stream()
            .map(messageMapper::toMessageResponse)
            .toList();
    }

    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        ChatEntity chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String recipientId = getRecipientId(chat, authentication);

        messageRepository.setMessageToSeenByChatId(chat.getId(), MessageState.SEEN);

        Notification notification = Notification.builder()
            .chatId(chat.getId())
            .type(NotificationType.SEEN)
            .receiverId(recipientId)
            .senderId(getSenderId(chat, authentication))
            .build();

        notificationService.sendNotification(recipientId, notification);
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        ChatEntity chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);

        MessageEntity message = new MessageEntity();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setType(MessageType.IMAGE);
        message.setState(MessageState.SENT);
        message.setMediaFilePath(filePath);

        messageRepository.save(message);

        Notification notification = Notification.builder()
            .chatId(chat.getId())
            .type(NotificationType.IMAGE)
            .messageType(MessageType.IMAGE)
            .senderId(senderId)
            .receiverId(recipientId)
            .media(fileService.readFileFromLocation(filePath))
            .build();

        notificationService.sendNotification(recipientId, notification);
    }

    public String getRecipientId(ChatEntity chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }

    public String getSenderId(ChatEntity chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }
}