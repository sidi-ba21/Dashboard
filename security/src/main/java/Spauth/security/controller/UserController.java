package Spauth.security.controller;

import Spauth.security.exception.ResourceNotFoundException;
import Spauth.security.model.User;
import Spauth.security.repository.UserRepository;
import Spauth.security.security.CurrentUser;
import Spauth.security.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static Long userId = 0L;
    
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        UserController.userId = userPrincipal.getId();
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    public static Long getUserId() {
        return userId;
    }
}