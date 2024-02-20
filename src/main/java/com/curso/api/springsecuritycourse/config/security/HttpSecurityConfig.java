package com.curso.api.springsecuritycourse.config.security;

import com.curso.api.springsecuritycourse.config.security.filter.JwtAuthenticationFilter;
import com.curso.api.springsecuritycourse.persistence.util.Role;
import com.curso.api.springsecuritycourse.persistence.util.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf( csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessConfig -> sessConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authReqConfig -> {
                    buildRequestMatchers(authReqConfig);
                } )
                .build();
    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*Productos*/
        authReqConfig.requestMatchers(HttpMethod.GET,"/products")
                .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.GET,"/products/{productId}")
                .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.POST,"/products")
                .hasRole(Role.ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}")
                .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}/disabled")
                .hasRole(Role.ADMINISTRATOR.name());
        /*Categorias*/
        authReqConfig.requestMatchers(HttpMethod.GET,"/categories")
                .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.GET,"/categories/{categoryId}")
                .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.POST,"/categories")
                .hasRole(Role.ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}")
                .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}/disabled")
                .hasRole(Role.ADMINISTRATOR.name());
        /*Perfil*/
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/profile")
                .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name(),Role.CUSTOMER.name());

        /*Public*/
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();
        /*Other*/
        authReqConfig.anyRequest().authenticated();
    }

}
