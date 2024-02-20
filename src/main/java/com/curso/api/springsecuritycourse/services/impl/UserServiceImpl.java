package com.curso.api.springsecuritycourse.services.impl;

import com.curso.api.springsecuritycourse.dto.SaveUser;
import com.curso.api.springsecuritycourse.exception.InvalidPasswordException;
import com.curso.api.springsecuritycourse.persistence.entity.User;
import com.curso.api.springsecuritycourse.persistence.repository.UserRepository;
import com.curso.api.springsecuritycourse.persistence.util.Role;
import com.curso.api.springsecuritycourse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createOneCustomer(SaveUser newUser) {

        validatePassword(newUser);

        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setName(newUser.getName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(Role.CUSTOMER);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validatePassword(SaveUser dto) {
        if(!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getConfirmPassword())){
            throw new InvalidPasswordException("Las contraseñas no coinciden");
        }
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new InvalidPasswordException("Las contraseñas no coinciden");
        }
    }
}
