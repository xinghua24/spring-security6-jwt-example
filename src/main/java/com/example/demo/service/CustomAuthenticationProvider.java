package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Qualifier("CustomAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  @Qualifier("CustomUserDetailsService")
  UserDetailsService userDetailsService;

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (userDetails != null) {
      if (passwordEncoder.matches(password, userDetails.getPassword())) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            username, password, userDetails.getAuthorities());
        return token;
      } else {
        throw new BadCredentialsException("Bad Credentials for "+ username);
      }
    }
    throw new UsernameNotFoundException(username + " not found.");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
