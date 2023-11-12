package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        Optional<User> user = userRepository.findByUsername(registerRequest.getUsername());
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        User newUser = new User().setUsername(registerRequest.getUsername())
            .setPassword(passwordEncoder.encode(registerRequest.getPassword()))
            .setEnabled(true)
            .setRoles("ROLE_USER")
            .setCreated(ZonedDateTime.now());
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody SigninRequest signinRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            if (authentication != null) {
                return ResponseEntity.ok(jwtUtils.generate(signinRequest.getUsername(), authentication.getAuthorities()));
            }
        } catch (AuthenticationException e) {
            log.error("Authentication Error", e);
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.error("Authentication Error");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
