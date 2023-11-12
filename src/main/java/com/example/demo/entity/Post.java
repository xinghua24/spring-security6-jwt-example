package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Entity
@Data
@Accessors(chain = true)
public class Post {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE", length = 255)
    private String title;

    @Column(name = "CONTENT", columnDefinition = "text")
    private String content;

    @Column(name = "CREATED")
    private ZonedDateTime created;

    @Column(name = "UPDATED")
    private ZonedDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
