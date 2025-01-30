package com.jhf.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhf.dto.Role;
import com.jhf.dto.UserBasicInfoDTO;
import com.jhf.models.User;
import com.jhf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService  implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' is not found", username)));
    }
    // Convert User entity to UserBasicInfoDTO
    public UserBasicInfoDTO convertToDTO(User user) {
        return new UserBasicInfoDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getBio(),
                user.getPassword()
        );
    }

    // Convert UserBasicInfoDTO to User entity
    public User convertToEntity(UserBasicInfoDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setGender(userDTO.getGender());
        user.setBio(userDTO.getBio());
        String password = userDTO.getPassword();
        if (password != null && !password.isEmpty()) {
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        } else {
            // Handle the case where password is null or empty, if needed
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return user;
    }


    // Get all users as DTOs
    public List<UserBasicInfoDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get user by username as DTO
    public Optional<UserBasicInfoDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDTO);
    }

    // Add new user using DTO
    public UserBasicInfoDTO addUser(UserBasicInfoDTO userDto) {
        User user = convertToEntity(userDto);
        user.setRole(Role.USER);
//        user.setComments(SetC);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    // Add new user using DTO
    public UserBasicInfoDTO addAdmin(UserBasicInfoDTO userDto) {
        User user = convertToEntity(userDto);
        user.setRole(Role.ADMIN);
//        user.setComments(SetC);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }


    public UserBasicInfoDTO updateUser(String username, UserBasicInfoDTO userDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields only if the value is not null or empty
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPhone() != null && !userDto.getPhone().isEmpty()) {
            user.setPhone(userDto.getPhone());
        }
        if (userDto.getFirstName() != null && !userDto.getFirstName().isEmpty()) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null && !userDto.getLastName().isEmpty()) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getDateOfBirth() != null) {
            user.setDateOfBirth(userDto.getDateOfBirth());
        }
        if (userDto.getGender() != null && !userDto.getGender().isEmpty()) {
            user.setGender(userDto.getGender());
        }
        if (userDto.getBio() != null && !userDto.getBio().isEmpty()) {
            user.setBio(userDto.getBio());
        }

        // Save the updated user to the repository
        User updatedUser = userRepository.save(user);

        // Convert the updated user back to a DTO and return it
        return convertToDTO(updatedUser);
    }


    // Delete user by username
    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }
}
