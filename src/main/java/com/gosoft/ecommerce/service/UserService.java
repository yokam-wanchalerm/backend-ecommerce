package com.gosoft.ecommerce.service;

import com.gosoft.ecommerce.entity.User;
import com.gosoft.ecommerce.model.UserUpdateRequest;
import com.gosoft.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public List<User> searchUsers(String search) {
        if (search != null && !search.isBlank() && !search.isEmpty()){
            return userRepository.findByEmailOrFirstNameOrLastNameLike(search);
        }

        return allUsers();
    }

    public User updateUser(Integer userID, UserUpdateRequest request) throws Exception {

        User user = userRepository.findById(userID).orElseThrow(() -> new Exception("User not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    public void deleteUser(Integer userID) throws Exception {
        User user = userRepository.findById(userID).orElseThrow(() -> new Exception("User not found"));
        userRepository.delete(user);
    }
}