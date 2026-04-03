package com.bohemian.intellect.controller;

import com.bohemian.intellect.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping({"user"})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("profile")
    public ResponseEntity<?> getProfile() {
        return userService.getUser();
    }

    @GetMapping({"{username}"})
    public ResponseEntity<?> getUserByUsername(@PathVariable @NotBlank(message = "Username is required") String username) {
        return this.userService.getUserByUsername(username);
    }
}
