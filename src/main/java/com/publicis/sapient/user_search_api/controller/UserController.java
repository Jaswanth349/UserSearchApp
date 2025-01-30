package com.publicis.sapient.user_search_api.controller;

import com.publicis.sapient.user_search_api.Exception.GlobalExceptionHandler;
import com.publicis.sapient.user_search_api.model.User;
import com.publicis.sapient.user_search_api.service.SearchService;
import com.publicis.sapient.user_search_api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final SearchService searchService;

    public UserController(UserService userService,SearchService searchService) {
        this.userService = userService;
        this.searchService = searchService;
    }


    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String search) {
        logger.info("Search initiated for keyword: {}", search);
        List<User> users = searchService.searchUsers(search);
        logger.info("Found {} users matching search keyword: {}", users.size(), search);
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{idOrEmail}")
    public ResponseEntity<?> getUser(@PathVariable String idOrEmail) {
        try {
            Long id = Long.parseLong(idOrEmail);
            Optional<User> user = userService.getUserByIdOrEmail(id, idOrEmail);
            if (user.isPresent()) {
                logger.info("User found with ID: {}", id);
                return ResponseEntity.ok(user.get());
            } else {
                logger.warn("User not found with ID or email: {}", idOrEmail);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid ID format provided: {}", idOrEmail, e);
            GlobalExceptionHandler.ErrorResponse errorResponse = new GlobalExceptionHandler.ErrorResponse("Invalid ID format. Please provide a numeric ID or a valid email.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }

    @GetMapping("/getALLUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(users);
    }


}

