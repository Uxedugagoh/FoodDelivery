package com.example.fooddelivery.service;

import com.example.fooddelivery.entity.UserEntity;
import com.example.fooddelivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAll() {
        // JPA repos
        return userRepository.findAll();
    }

    public UserEntity register(UserEntity userEntity) {
        if (userRepository.findByLoginIgnoreCase(userEntity.getLogin()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        userRepository.save(userEntity);
        return userEntity;
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id = " + id + " not found"));
    }

    public UserEntity changeUserProfile(Long id, UserEntity userEntity) {
        UserEntity currentUserEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id = " + id + " not found"));
        if (userRepository.findByLoginIgnoreCase(userEntity.getLogin()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        currentUserEntity.setLogin(userEntity.getLogin());
        currentUserEntity.setPassword(userEntity.getPassword());
        currentUserEntity.setRole(userEntity.getRole());
        userRepository.save(currentUserEntity);
        return currentUserEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByLoginIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with login = " + username + " not found"));
        Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().toAuthority());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
    }
}