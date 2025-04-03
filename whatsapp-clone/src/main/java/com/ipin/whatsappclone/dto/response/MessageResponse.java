package com.ipin.whatsappclone.dto.response;

import java.time.LocalDateTime;

import com.ipin.whatsappclone.constants.MessageState;
import com.ipin.whatsappclone.constants.MessageType;

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
public class MessageResponse {
    
    Long id;
    String content;
    MessageType type;
    MessageState state;
    String senderId;
    String receiverId;
    LocalDateTime createdAt;
    byte[] media;
}
