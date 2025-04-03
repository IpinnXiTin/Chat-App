package com.ipin.whatsappclone.entity;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Transient;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
@NamedQuery(name = "UserConstants.FIND_USER_BY_EMAIL",
            query = "select u from UserEntity u where u.email = :email")
@NamedQuery(name = "UserConstants.FIND_ALL_USERS_EXCEPT_SELF",
            query = "select u from UserEntity u where u.id != :publicId")
@NamedQuery(name = "UserConstants.FIND_USER_BY_PUBLIC_ID",
            query = "select u from UserEntity u where u.id = :publicId")
public class UserEntity extends BaseAuditingEntity {
    
    private static final int LAST_ACTIVE_INTERVAL = 5;
    @Id
    String id;
    String firstName;
    String lastName;
    String email;
    LocalDateTime lastSeen;
    
    @OneToMany(mappedBy = "sender")
    List<ChatEntity> chatsAsSender;
    
    @OneToMany(mappedBy = "recipient")
    List<ChatEntity> chatsAsRecipient;
    
    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }
}