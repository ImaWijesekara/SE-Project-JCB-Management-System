package com.se.jcb_mng.services;

import com.se.jcb_mng.entities.User;
import com.se.jcb_mng.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Encrypt the password before saving
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        // Ensure default role is assigned if none provided (e.g., CUSTOMER)
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("CUSTOMER");
        }

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateProfile(Long userId, String fullName, String phoneNumber, String address) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setFullName(fullName);
        existingUser.setPhoneNumber(phoneNumber);
        existingUser.setAddress(address);

        return userRepository.save(existingUser);
    }

}
