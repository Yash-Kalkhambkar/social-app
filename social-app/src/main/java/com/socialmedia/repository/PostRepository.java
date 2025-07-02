package com.socialmedia.repository;

import com.socialmedia.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT p FROM Post p WHERE p.tags LIKE %:tag% ORDER BY p.createdAt DESC")
    List<Post> findByTagsContainingOrderByCreatedAtDesc(String tag);
}