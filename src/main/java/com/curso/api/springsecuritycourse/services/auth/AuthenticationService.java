package com.curso.api.springsecuritycourse.services.auth;

import com.curso.api.springsecuritycourse.dto.RegisterUser;
import com.curso.api.springsecuritycourse.dto.SaveUser;
import com.curso.api.springsecuritycourse.dto.auth.AuthRequest;
import com.curso.api.springsecuritycourse.dto.auth.AuthResponse;
import com.curso.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.curso.api.springsecuritycourse.persistence.entity.User;
import com.curso.api.springsecuritycourse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisterUser registerOneCustomer(SaveUser newUser) {
        User user = userService.createOneCustomer(newUser);
        RegisterUser userDto = new RegisterUser();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user,generateExtraClaims(user));

        userDto.setJwt(jwt);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extra = new HashMap<>();
        extra.put("name",user.getName());
        extra.put("role",user.getRole().name());
        extra.put("authorities",user.getAuthorities());
        return extra;
    }

    public AuthResponse login(AuthRequest authRequest) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        authenticationManager.authenticate(auth);

        UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();

        String jwt = jwtService.generateToken(user,generateExtraClaims((User) user));

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);

        return authResponse;

    }

    public Boolean validateToken(String jwt) {

        try {
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    public User findLoggedInUser() {
        Authentication authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authToken.getPrincipal();
        return userService.findOneByUsername(username)
                .orElseThrow( () -> new ObjectNotFoundException("User Not Found with username: "+username));
    }
}
