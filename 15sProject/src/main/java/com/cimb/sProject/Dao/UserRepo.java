package com.cimb.sProject.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.sProject.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	public Optional<User> findByUserName(String userName);

}
