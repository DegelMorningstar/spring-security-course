package com.curso.api.springsecuritycourse.config.security.filter;

import com.curso.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.curso.api.springsecuritycourse.services.UserService;
import com.curso.api.springsecuritycourse.services.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //obtener encabezado
        String authHeader = request.getHeader("Authorization");
        if(!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        //obtener token
        String jwt = authHeader.split(" ")[1];
        //obtener subject - valida token
        String username = jwtService.extractUsername(jwt);
        //set objeto auth dentro del context holder
        UserDetails userDetails = userService.findOneByUsername(username)
                .orElseThrow( () -> new ObjectNotFoundException("User Not Found with username: "+username));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,null, userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        //seguir con los demas filtros
        filterChain.doFilter(request,response);
    }
}
