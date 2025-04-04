package com.ipin.whatsappclone.mapper;

import org.springframework.stereotype.Service;

import com.ipin.whatsappclone.dto.response.MessageResponse;
import com.ipin.whatsappclone.entity.MessageEntity;
import com.ipin.whatsappclone.service.FileService;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(MessageEntity messageEntity) {
        return MessageResponse.builder()
            .id(messageEntity.getId())
            .content(messageEntity.getContent())
            .type(messageEntity.getType())
            .state(messageEntity.getState())
            .senderId(messageEntity.getSenderId())
            .receiverId(messageEntity.getReceiverId())
            .createdAt(messageEntity.getCreatedDate())
            .media(FileService.readFileFromLocation(messageEntity.getMediaFilePath()))
            .build();
    } 

    
}
