package com.demo.springlogin.services;

import com.demo.springlogin.models.Role;
import com.demo.springlogin.models.User;
import com.demo.springlogin.repositories.RoleRepository;
import com.demo.springlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setIsActive(true);
        Role role = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        return userRepository.save(user);
    }
}
