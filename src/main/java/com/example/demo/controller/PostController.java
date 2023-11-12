package com.example.demo.controller;

import com.example.demo.entity.Post;
import com.example.demo.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  @Autowired
  private PostRepository postRepository;

  @GetMapping("")
  @PreAuthorize("hasRole('ROLE_USER')")
  public List<Post> getPosts() {
    return postRepository.findRecentPosts();
  }
}
