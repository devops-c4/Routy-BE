package com.c4.routy.domain.user.repository;


import com.c4.routy.domain.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = :userEmail")
    UserEntity findByUserEmail(@Param("userEmail") String userEmail);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :username and u.phone = :phone")
    UserEntity findByUserEmailAndPhone(@Param("username") String username, @Param("phone") String phone);
}
