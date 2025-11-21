package com.codewithgaurav.jwtsecurity.service;

import com.codewithgaurav.jwtsecurity.dto.User;
import com.codewithgaurav.jwtsecurity.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

    final UserRepo userRepo;

    public CustomUserDetails(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username != null){
            User user = userRepo.findByUsername(username).orElse(null);
            if(user == null){
                throw new UsernameNotFoundException("Username not found");
            }else{
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles("USER").build();
            }
        }else{
            throw new UsernameNotFoundException("Username not found");
        }
    }

}
