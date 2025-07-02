package com.socialmedia.controller;

import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.entity.Comment;
import com.socialmedia.entity.Like;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private User getCurrentUser(String token) {
        String username = jwtUtil.getUsernameFromToken(token.substring(7));
        return userRepository.findByUsername(username).orElse(null);
    }
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPosts() {
        List<Post> posts = postRepository.findAllOrderByCreatedAtDesc();
        List<Map<String, Object>> response = posts.stream().map(this::convertToMap).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) {
        User user = getCurrentUser(token);
        if (user == null) return ResponseEntity.badRequest().body("Invalid token");
        
        Post post = new Post(request.get("content"), user);
        post.setImageUrl(request.get("imageUrl"));
        post.setTags(request.get("tags"));
        
        postRepository.save(post);
        return ResponseEntity.ok(convertToMap(post));
    }
    
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> toggleLike(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        User user = getCurrentUser(token);
        if (user == null) return ResponseEntity.badRequest().body("Invalid token");
        
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) return ResponseEntity.badRequest().body("Post not found");
        
        Post post = postOpt.get();
        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(user.getId(), postId);
        
        if (existingLike.isPresent()) {
            likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
            return ResponseEntity.ok(Map.of("liked", false, "likesCount", likeRepository.countByPostId(postId)));
        } else {
            Like like = new Like(user, post);
            likeRepository.save(like);
            return ResponseEntity.ok(Map.of("liked", true, "likesCount", likeRepository.countByPostId(postId)));
        }
    }
    
    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> addComment(@RequestHeader("Authorization") String token, @PathVariable Long postId, @RequestBody Map<String, String> request) {
        User user = getCurrentUser(token);
        if (user == null) return ResponseEntity.badRequest().body("Invalid token");
        
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) return ResponseEntity.badRequest().body("Post not found");
        
        Post post = postOpt.get();
        Comment comment = new Comment(request.get("content"), user, post);
        commentRepository.save(comment);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", comment.getId());
        response.put("content", comment.getContent());
        response.put("createdAt", comment.getCreatedAt());
        response.put("user", Map.of("username", user.getUsername()));
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Map<String, Object>>> getComments(@PathVariable Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        List<Map<String, Object>> response = comments.stream().map(comment -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", comment.getId());
            map.put("content", comment.getContent());
            map.put("createdAt", comment.getCreatedAt());
            map.put("user", Map.of("username", comment.getUser().getUsername()));
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    private Map<String, Object> convertToMap(Post post) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", post.getId());
        map.put("content", post.getContent());
        map.put("imageUrl", post.getImageUrl());
        map.put("tags", post.getTags());
        map.put("createdAt", post.getCreatedAt());
        map.put("user", Map.of("username", post.getUser().getUsername()));
        map.put("likesCount", likeRepository.countByPostId(post.getId()));
        map.put("commentsCount", post.getComments().size());
        return map;
    }
}