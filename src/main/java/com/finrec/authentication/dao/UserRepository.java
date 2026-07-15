package com.finrec.authentication.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finrec.authentication.model.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster, Long> {
	Optional<UserMaster> findByUsername(String username);
}
