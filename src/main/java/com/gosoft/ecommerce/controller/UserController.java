package com.gosoft.ecommerce.controller;

import com.gosoft.ecommerce.entity.User;
import com.gosoft.ecommerce.enums.Type;
import com.gosoft.ecommerce.model.UserUpdateRequest;
import com.gosoft.ecommerce.response.GlobalResponse;
import com.gosoft.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
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
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adminuser/profile")
    public ResponseEntity<GlobalResponse<?>> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        GlobalResponse<User> response = new GlobalResponse<>(currentUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/user/{userID}")
    public ResponseEntity<?> getUser(@PathVariable Integer userID) {
        Optional<User> users = userService.getUserByID(userID);
        if (users.isEmpty()) {
            return new ResponseEntity<>(new GlobalResponse<>(Type.USER_NOT_FOUND, userID), HttpStatus.BAD_REQUEST);
        }

        GlobalResponse<User> response = new GlobalResponse<>(users.get());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<GlobalResponse<?>> getUsers(@RequestParam String search) {
        List<User> users = userService.searchUsers(search);
        GlobalResponse<List<User>> response = new GlobalResponse<>(users);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/user/{userID}")
    public ResponseEntity<GlobalResponse<?>> updateUser(@PathVariable Integer userID, @RequestBody UserUpdateRequest request) throws Exception {
        Optional<User> users = userService.getUserByID(userID);
        if (users.isEmpty()) {
            return new ResponseEntity<>(new GlobalResponse<>(Type.USER_NOT_FOUND, userID), HttpStatus.BAD_REQUEST);
        }

        User user = userService.updateUser(userID, request);
        GlobalResponse<User> response = new GlobalResponse<>(user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/user/{userID}")
    public ResponseEntity<GlobalResponse<?>> deleteUser(@PathVariable Integer userID) throws Exception {
        Optional<User> users = userService.getUserByID(userID);
        if (users.isEmpty()) {
            return new ResponseEntity<>(new GlobalResponse<>(Type.USER_NOT_FOUND, userID), HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(userID);
        return ResponseEntity.ok(new GlobalResponse<>());
    }
}
