package com.omdev.um.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omdev.um.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	//Query will generate automatically
	public UserEntity findByEmail(String email);
	
	//Query will generate automatically
	public UserEntity findByEmailAndPassword(String email, String password);
	
}
