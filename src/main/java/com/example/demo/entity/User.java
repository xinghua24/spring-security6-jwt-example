package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@JsonIncludeProperties({"name", "bio"})
@JsonPropertyOrder({"name", "bio"})
public class User implements UserDetails {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 70, nullable = false)
    private String password;

    @Column(name = "EMAIL", length = 70)
    private String email;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "ENABLED", nullable = false, columnDefinition = "TINYINT")
    private boolean enabled;

    @Column(name = "ROLES", length = 70)
    private String roles;

    @Column(name = "CREATED")
    private ZonedDateTime created;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
