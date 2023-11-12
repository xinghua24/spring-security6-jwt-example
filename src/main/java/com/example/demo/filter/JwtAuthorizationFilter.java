package com.example.demo.filter;

import com.example.demo.service.JwtUtils;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  @Autowired
  JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // Authorization Bearer token
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = authorizationHeader.split(" ")[1].trim();

    if (!jwtUtils.validate(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    String username = jwtUtils.getUsername(token);
    String roles = jwtUtils.getRoles(token);
    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        username, null, authorities);
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authToken);
    filterChain.doFilter(request, response);
  }
}
