package com.codewithgaurav.jwtsecurity.service.impl;

import com.codewithgaurav.jwtsecurity.dto.User;
import com.codewithgaurav.jwtsecurity.repo.UserRepo;
import com.codewithgaurav.jwtsecurity.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final PasswordEncoder passwordEncoder;

    final UserRepo userRepo;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public boolean registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
