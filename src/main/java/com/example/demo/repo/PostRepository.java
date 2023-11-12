package com.example.demo.repo;

import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from post order by created desc limit 0,100", nativeQuery = true)
    List<Post> findRecentPosts();

    @Query(value = "select * from post join user on post.user_id = user.id where user.id = :userId order by created desc limit 0,100", nativeQuery = true)
    List<Post> findRecentPostsByUser(@Param("userId") long userId);
}
