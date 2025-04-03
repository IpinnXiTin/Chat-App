package com.ipin.whatsappclone.entity;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

import com.ipin.whatsappclone.constants.ChatConstants;
import com.ipin.whatsappclone.constants.MessageState;
import com.ipin.whatsappclone.constants.MessageType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@Table(name = "chat")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID,
            query = "select distinct c from ChatEntity c where c.sender.id = :senderId or c.recipient.id = :senderId order by createdDate desc")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER,
            query = "select distinct c from ChatEntity c where (c.sender.id = :senderId and c.recipient.id = :recipientId) or (c.sender.id = :recipientId and c.recipient.id = :senderId)")
            
public class ChatEntity extends BaseAuditingEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    UserEntity recipient;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC")
    List<MessageEntity> messages;

    @Transient
    public String getChatName(final String senderId) {
        if (recipient.getId().equals(senderId)) {
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return recipient.getFirstName() + " " + recipient.getLastName();
    }

    @Transient
    public long getUnreadMessages(final String senderId) {
        return messages
            .stream()
            .filter(m -> m.getReceiverId().equals(senderId))
            .filter(m -> MessageState.SENT == m.getState())
            .count();
    }

    @Transient
    public String getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            if (messages.get(0).getType() != MessageType.TEXT) {
                return "Attachment";
            }
            return messages.get(0).getContent();
        }
        return null;
    }

    @Transient
    public LocalDateTime getLastMessageTime() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(0).getCreatedDate();
        }
        return null;
    }
}
