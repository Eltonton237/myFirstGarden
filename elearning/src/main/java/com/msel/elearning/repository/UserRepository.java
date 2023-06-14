package com.msel.elearning.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.msel.elearning.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByName(String name);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByPassword(String password);
	
}
