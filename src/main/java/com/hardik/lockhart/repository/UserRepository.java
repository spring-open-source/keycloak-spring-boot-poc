package com.hardik.lockhart.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.lockhart.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Boolean existsByEmailId(String emailId);

}
