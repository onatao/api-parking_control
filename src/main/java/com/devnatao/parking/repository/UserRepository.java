package com.devnatao.parking.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devnatao.parking.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
	
	Optional<UserModel> findByUsername(String username);
}