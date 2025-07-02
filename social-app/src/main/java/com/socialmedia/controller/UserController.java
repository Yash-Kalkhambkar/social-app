package com.socialmedia.controller;

import com.socialmedia.entity.User;
import com.socialmedia.repository.UserRepository;
import com.socialmedia.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsernameFromToken(token.substring(7));
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        
        User user = userOpt.get();
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("profilePic", user.getProfilePic() != null ? user.getProfilePic() : "");
        response.put("bio", user.getBio() != null ? user.getBio() : "");
        response.put("postsCount", user.getPosts().size());
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) {
        String username = jwtUtil.getUsernameFromToken(token.substring(7));
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        
        User user = userOpt.get();
        if (request.containsKey("profilePic")) {
            user.setProfilePic(request.get("profilePic"));
        }
        if (request.containsKey("bio")) {
            user.setBio(request.get("bio"));
        }
        
        userRepository.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("profilePic", user.getProfilePic() != null ? user.getProfilePic() : "");
        response.put("bio", user.getBio() != null ? user.getBio() : "");
        
        return ResponseEntity.ok(response);
    }
}