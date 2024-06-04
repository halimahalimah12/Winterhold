package com.indocyber.Winterhold.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                        request.requestMatchers("/resources/**","/error/403").permitAll()
                                .requestMatchers("/register/**", "/login").anonymous()
                                .anyRequest().authenticated()
                ).formLogin(login->login
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticating")
                        .failureUrl("/login?error=true"))
                .logout(logout ->logout
                        .logoutUrl("/logout"));//
        return http.build();
    }


}
