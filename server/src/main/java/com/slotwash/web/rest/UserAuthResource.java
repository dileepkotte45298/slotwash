package com.slotwash.web.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.slotwash.errors.BadCredentialsException;
import com.slotwash.models.User;
import com.slotwash.repository.UserRepository;
import com.slotwash.security.JwtTokenGenerator;
import com.slotwash.security.manager.CustomAuthenticationManager;
import com.slotwash.security.provider.CustomUserDetailsService;
import com.slotwash.web.rest.dto.Login;

import java.time.Duration;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthResource{

    @Autowired BCryptPasswordEncoder encoder;
    @Autowired CustomAuthenticationManager customAuthenticationManager;
    @Autowired CustomUserDetailsService customUserDetailsService;
    @Autowired JwtTokenGenerator jwtTokenGenerator;
    @Autowired UserRepository userRespository;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        Optional<User> userexists = userRespository.findById(user.getUserName());
        if (userexists.isPresent()) {
            return ResponseEntity.badRequest().body("User With this username already exists! ");
        }
        String encodedUserpassword = encoder.encode(user.getPassword());
        user.setPassword(encodedUserpassword);
        userRespository.save(user);
        return ResponseEntity.ok("User Created Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Login login) {
        String username = login.getUsername();
        String password = login.getPassword();
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = customAuthenticationManager.authenticate(authentication);
        if (auth.isAuthenticated()) {
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(login.getUsername());
            Duration duration = Duration.ofHours(12);
            if(login.isRememberMe()) duration = Duration.ofDays(30);
            final String token = jwtTokenGenerator.generateToken(userDetails, duration);
            return ResponseEntity.ok(token);
        }
        throw new BadCredentialsException("Invalid Username or Password !");
    }
}

