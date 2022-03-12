package com.phenomenal.shop.service;

import com.phenomenal.shop.entity.User;
import com.phenomenal.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService{
    @Autowired
    UserRepository userRepository;

    public User getUserSettings(Authentication authentication) {
        Optional<User> userOptional = userRepository.findByUsername(authentication.getName());
        return userOptional.orElse(null);
    }
}