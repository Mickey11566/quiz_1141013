package com.example.quiz_1141013.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz_1141013.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

	@Transactional
	@Modifying
	@Query(value = "insert into user(email, password, name, phone) "//
			+ " values (?1, ?2, ?3, ?4)", nativeQuery = true)
	public int addUser(String email, String password, String name, String phone);

	@Query(value = "Select * from user where email = ?1", nativeQuery = true)
	public User getUser(String email);

	@Transactional
	@Modifying
	@Query(value = "update user set password = ?2, name = ?3, phone = ?4 where email = ?1", nativeQuery = true)
	public int updateInfo(String email, String password, String name, String phone);

}
