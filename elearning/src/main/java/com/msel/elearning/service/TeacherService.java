package com.msel.elearning.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.msel.elearning.model.Teacher;
import com.msel.elearning.repository.TeacherRepository;

@Service
public class TeacherService {
	@Autowired
	private TeacherRepository teacherRepository;

	public Optional<Teacher> getTeacher(final Long id) {
		return teacherRepository.findById(id);
	}

	public Iterable<Teacher> getTeachers() {
		return teacherRepository.findAll();
	}

	public void deleteTeacher(final Long id) {
		teacherRepository.deleteById(id);
	}

	public void saveTeacher(Teacher teacher) {
        try {
            teacherRepository.save(teacher);
        } catch (DataIntegrityViolationException e) {
            // La contrainte unique a été violée
            // Gérer l'exception ici
            // ...
        }
	}
		
	public Optional<Teacher> findTeacherByName(String name) {
		return teacherRepository.findByName(name);	
    }
	
	public Optional<Teacher> findTeacherByEmail(String email) {
		return teacherRepository.findByEmail(email);
    }
	
    public boolean isTeacherAlreadyExists(Teacher teacher) {
        return findTeacherByEmail(teacher.getEmail()).isPresent() && findTeacherByName(teacher.getName()).isPresent() ;
    }
    
    public boolean isValidContact(String contact) {
    	String regex = "^6[0-9]{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }
    
    public Teacher buildTeacher(String name, String email, String contact) {
    	Teacher teacher = new Teacher();
    	teacher.setName(name);
    	teacher.setEmail(email);
    	teacher.setContact(contact);
    	teacher.setPassword();
    	teacher.setSubscriptionDate(LocalDate.now());
    	
    	return teacher;
    }
    
}
