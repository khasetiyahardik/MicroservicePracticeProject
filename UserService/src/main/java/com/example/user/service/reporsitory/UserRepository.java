package com.example.user.service.reporsitory;

import com.example.user.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM user_entity WHERE email=:email",nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT * FROM user_entity WHERE name=:name",nativeQuery = true)
    Optional<UserEntity> findByName(String name);
}
