package com.c4.routy.domain.user.repository;


import com.c4.routy.domain.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    UserEntity findByUserEmail(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username and u.phone = :phone")
    UserEntity findUserEntitiesByUsernameAndPhone(@Param("username") String username,@Param("phone") String phone);
}
