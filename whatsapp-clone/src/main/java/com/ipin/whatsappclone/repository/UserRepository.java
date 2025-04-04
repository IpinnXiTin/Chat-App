package com.ipin.whatsappclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipin.whatsappclone.constants.UserConstants;
import com.ipin.whatsappclone.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query(name = UserConstants.FIND_USER_BY_EMAIL)
    Optional<UserEntity> findByEmail(@Param("email") String userEmail);

    @Query(name = UserConstants.FIND_USER_BY_PUBLIC_ID)
    Optional<UserEntity> findByPublicId(@Param("publicId") String publicId);

    @Query(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF)
    List<UserEntity> findAllUsersExceptSelf(@Param("publicId") String publicId);
}
