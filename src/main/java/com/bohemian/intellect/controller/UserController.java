package com.bohemian.intellect.controller;

import com.bohemian.intellect.model.User;
import com.bohemian.intellect.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"user"})
public class UserController {
    @Autowired
    UserService userService;

    public UserController() {
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("profile")
    public ResponseEntity<?> getProfile() {
        return userService.getUser();
    }

    @GetMapping({"{username}"})
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return this.userService.getUserByUsername(username);
    }
}
