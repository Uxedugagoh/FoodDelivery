package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.UserDto;
import com.example.fooddelivery.dto.UserRole;
import com.example.fooddelivery.entity.UserEntity;
import com.example.fooddelivery.mapper.UserEntityMapper;
import com.example.fooddelivery.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityMapper userEntityMapper;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findAllByRole(UserRole role) {
        return userRepository.findAllByRole(role);
    }

    public UserEntity register(UserDto userDto) {
        if (userRepository.findByLoginIgnoreCase(userDto.getLogin()).isPresent()) {
            throw new EntityExistsException("Login already taken");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(userEntityMapper.toEntity(userDto));
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " not found"));
    }

    public UserEntity changeUserProfile(UserDto userDto) {
        UserEntity currentUserEntity = getCurrentUser();

        if (userDto.getLogin() != null && userRepository.findByLoginIgnoreCase(userDto.getLogin()).isPresent()) {
            throw new EntityExistsException("Username already taken");
        }
        userEntityMapper.updateUserEntity(currentUserEntity, userDto);
        if (userDto.getPassword() != null) {
            currentUserEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(currentUserEntity);
        return currentUserEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByLoginIgnoreCase(username)
                .orElseThrow(() -> new EntityNotFoundException("User with login = " + username + " not found"));
        Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().toAuthority());
        return new User(user.getLogin(), user.getPassword(), roles);
    }

    public UserEntity deleteUserById(Long id) {
        UserEntity user = getUserById(id);
        userRepository.delete(user);
        return user;
    }

    public UserEntity getCurrentUser() {
        User details = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByLoginIgnoreCase(details.getUsername())
                .orElseThrow();
    }
}