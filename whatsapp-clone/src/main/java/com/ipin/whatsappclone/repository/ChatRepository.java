package com.ipin.whatsappclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipin.whatsappclone.constants.ChatConstants;
import com.ipin.whatsappclone.entity.ChatEntity;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, String> {

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    List<ChatEntity> findChatBySenderId(@Param("senderId") String userId);

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER)
    Optional<ChatEntity> findChatByReceiverAndSender(@Param("senderId") String senderId, @Param("recipientId") String receiverId);
    
}
