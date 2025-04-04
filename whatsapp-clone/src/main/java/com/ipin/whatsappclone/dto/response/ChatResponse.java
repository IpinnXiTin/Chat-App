package com.ipin.whatsappclone.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatResponse {
    
    String id;
    String name;
    long unreadCount;
    String lastMessage;
    LocalDateTime lastMessageTime;
    boolean isRecipientOnline;
    String senderId;
    String recipientId;
}
