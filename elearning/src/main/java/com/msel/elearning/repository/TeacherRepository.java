package com.msel.elearning.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.msel.elearning.model.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

	Optional<Teacher> findByName(String name);
	
	Optional<Teacher> findByEmail(String email);
	
	Optional<Teacher> findByPassword(String password);
}
