package com.ipin.whatsappclone.mapper;

import org.springframework.stereotype.Service;

import com.ipin.whatsappclone.dto.response.ChatResponse;
import com.ipin.whatsappclone.entity.ChatEntity;

@Service
public class ChatMapper {
    public ChatResponse toChatResponse(ChatEntity chat, String userId) {
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(userId))
                .unreadCount(chat.getUnreadMessages(userId))
                .lastMessage(chat.getLastMessage())
                .lastMessageTime(chat.getLastMessageTime())
                .isRecipientOnline(chat.getRecipient().isUserOnline())
                .senderId(chat.getSender().getId())
                .recipientId(chat.getRecipient().getId())
                .build();
    }
}
