package com.curso.api.springsecuritycourse.controllers;

import com.curso.api.springsecuritycourse.dto.auth.AuthRequest;
import com.curso.api.springsecuritycourse.dto.auth.AuthResponse;
import com.curso.api.springsecuritycourse.persistence.entity.User;
import com.curso.api.springsecuritycourse.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        Boolean isTokenValid = authService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid AuthRequest authRequest
    ){
        AuthResponse authResponse = authService.login(authRequest);

        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findMyProfile(){
        User user = authService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }
}
