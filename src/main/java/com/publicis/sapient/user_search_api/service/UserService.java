package com.publicis.sapient.user_search_api.service;

import com.publicis.sapient.user_search_api.model.User;
import com.publicis.sapient.user_search_api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
        logger.info("User with ID {} saved successfully", user.getId());
    }

    public Optional<User> getUserByIdOrEmail(Long id, String email) {
        return userRepository.findByIdOrEmail(id, email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

