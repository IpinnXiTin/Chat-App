package com.ipin.whatsappclone.dto.response;

import com.ipin.whatsappclone.constants.MessageType;
import com.ipin.whatsappclone.constants.NotificationType;

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
public class Notification {
    
    String chatId;
    String content;
    String senderId;
    String receiverId;
    String chatName;
    MessageType messageType;
    NotificationType type;
    byte[] media;
}
