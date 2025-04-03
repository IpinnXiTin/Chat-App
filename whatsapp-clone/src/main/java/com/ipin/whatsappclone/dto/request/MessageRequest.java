package com.ipin.whatsappclone.dto.request;

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
public class MessageRequest {

    String content;
    String senderId;
    String receiverId;
    MessageType type;
    String chatId;
}