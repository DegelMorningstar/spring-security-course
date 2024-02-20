package com.curso.api.springsecuritycourse.services;

import com.curso.api.springsecuritycourse.dto.SaveUser;
import com.curso.api.springsecuritycourse.persistence.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User createOneCustomer(SaveUser newUser);

    Optional<User> findOneByUsername(String username);
}
