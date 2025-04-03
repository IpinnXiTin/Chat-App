package com.ipin.whatsappclone.entity;

import com.ipin.whatsappclone.constants.MessageConstants;
import com.ipin.whatsappclone.constants.MessageState;
import com.ipin.whatsappclone.constants.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedQuery(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID,
            query = "select m from MessageEntity m where m.chat.id = :chatId order by m.createdDate")
@NamedQuery(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT,
            query = "update MessageEntity set state = :newState where chat.id = :chatId")
public class MessageEntity extends BaseAuditingEntity {
    
    @Id
    @SequenceGenerator(name = "msg_seq", sequenceName = "msg_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_seq")
    long id;

    @Column(columnDefinition = "TEXT")
    String content;

    @Enumerated(EnumType.STRING)
    MessageState state;

    @Enumerated(EnumType.STRING)
    MessageType type;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    ChatEntity chat;

    @Column(name = "sender_id", nullable = false)
    String senderId;

    @Column(name = "receiver_id", nullable = false)
    String receiverId;
    String mediaFilePath;

}
