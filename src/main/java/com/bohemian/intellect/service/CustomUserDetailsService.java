package com.bohemian.intellect.service;

import com.bohemian.intellect.exception.UserNotFoundException;
import com.bohemian.intellect.model.CustomUserDetails;
import com.bohemian.intellect.model.User;
import com.bohemian.intellect.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("No user exist with username : " + username);
        } else {
            return new CustomUserDetails(user);
        }
    }
}
