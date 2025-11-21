package com.codewithgaurav.jwtsecurity.controller;

import com.codewithgaurav.jwtsecurity.dto.User;
import com.codewithgaurav.jwtsecurity.service.UserService;
import com.codewithgaurav.jwtsecurity.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    final UserService userService;

    final AuthenticationManager authenticationManager;


    AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        if(username != null && password != null) {
            boolean isRegistered = userService.registerUser(new User(username, password));
            if(isRegistered) {
                return "User registered successfully";
            }else{
                return "User not registered";
            }
        }else{
            return "User not registered";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        if(username != null && password != null) {
            Authentication auth =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if(auth.isAuthenticated()) {
//                SecurityContextHolder.getContext().setAuthentication(auth);
//                request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
//                return "User logged in successfully";
                return JWTUtil.generateToken(username);
            }else{
                return "User not logged in";
            }
        }else{
            return "User not found!";
        }
    }

    @PostMapping("/test")
    public String test(){
        return "test";
    }


}
