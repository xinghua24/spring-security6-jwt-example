package com.example.demo.service;

import com.example.demo.entity.MyUserDetails;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("CustomUserDetailsService")
class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.demo.entity.User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new MyUserDetails(user.get());
        }
        throw new UsernameNotFoundException(username);
    }
}
