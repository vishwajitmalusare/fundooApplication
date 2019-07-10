package com.bridgelabz.fundoonotes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmailId(String emailId);
}