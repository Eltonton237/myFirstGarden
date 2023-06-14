package com.msel.elearning.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.msel.elearning.model.SignupForm;
import com.msel.elearning.model.User;
import com.msel.elearning.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public Optional<User> getUser(final Long id) {
		return userRepository.findById(id);
	}

	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	public void deleteUser(final Long id) {
		userRepository.deleteById(id);
	}

	public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // La contrainte unique a été violée
            // Gérer l'exception ici
            // ...
        }
	}
	
	public Optional<User> findUserByName(String name) {
		return userRepository.findByName(name);	
    }
	
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
    }
	
    public boolean isUserAlreadyExists(User user) {
        return findUserByEmail(user.getEmail()).isPresent() && findUserByName(user.getName()).isPresent() ;
    }
	
	public User buildUser(SignupForm signupForm ) {
		User user = new User();
		
        user.setName(signupForm.getName());
        user.setEmail(signupForm.getEmail());
        user.setPassword(signupForm.getPassword());
        user.setSubscriptionDate(LocalDate.now());
	
        return user;
	}
}
