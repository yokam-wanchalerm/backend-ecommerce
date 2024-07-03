package com.gosoft.ecommerce.controller;

import com.gosoft.ecommerce.entity.User;
import com.gosoft.ecommerce.model.UserUpdateRequest;
import com.gosoft.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(@RequestParam String search) {
        List<User> users = userService.searchUsers(search);

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userID, @RequestBody UserUpdateRequest request) throws Exception {
        User user = userService.updateUser(userID, request);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userID) throws Exception {
        userService.deleteUser(userID);
        return ResponseEntity.ok("delete userID "+userID+" success");
    }
}
