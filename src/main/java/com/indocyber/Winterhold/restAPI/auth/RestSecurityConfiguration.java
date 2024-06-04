package com.indocyber.Winterhold.restAPI.auth;

import com.indocyber.Winterhold.restAPI.auth.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    public RestSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
                .csrf(request -> request.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/resources/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/authors").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/authors/").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/authors/").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/authors/").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(httpBasic ->httpBasic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandler -> exceptionHandler.accessDeniedHandler(accessDeniedHandler()));
        return http.build();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return ((request, response, authException) -> {
            response.setStatus(401);
            response.getWriter().write("unauthorized request header");
        }) ;
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            response.setHeader("Content-Type","application/json");
            response.setStatus(403);
            response.getWriter().write("{\"massage\":\""+accessDeniedException.getMessage()+"\"}");
        });
    }



}
