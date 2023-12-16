package com.example.authentication.repository;

import com.example.authentication.entity.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, String> {
    @Query(value = "SELECT * FROM authentication_entity WHERE name=:name", nativeQuery = true)
    Optional<AuthenticationEntity> findOneByName(String name);

    @Query(value = "SELECT * FROM authentication_entity WHERE name=:name", nativeQuery = true)
    AuthenticationEntity findByName(String name);


}
