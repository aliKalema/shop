package com.phenomenal.shop.service;

import com.phenomenal.shop.entity.User;
import com.phenomenal.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserService{
    @Autowired
    UserRepository userRepository;

    public User getUserSettings(Authentication authentication) {
        Optional<User> userOptional = userRepository.findByUsername(authentication.getName());
        if(userOptional.isEmpty()){
            return null;
        }
        userOptional.get().setPassword(null);
        return userOptional.get();
    }

    public List<User> getAllUsers(){
       List<User>users = new ArrayList<>();
       userRepository.findAll().forEach(users::add);
       return users;
    }
}