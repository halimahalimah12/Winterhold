package com.indocyber.Winterhold.restAPI.auth.jwt;

import com.indocyber.Winterhold.restAPI.auth.AuthFailureHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthFailureHandler failureHandler;

    public JwtRequestFilter(JwtService jwtService, UserDetailsService userDetailsService, AuthFailureHandler failureHandler) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                var token = authorizationHeader.substring(7);
                var claims= jwtService.getClaims(token);
                var username= claims.get("username", String.class);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtService.isValid(token,userDetails)){
//                    membuat authentication berdasarkan token yang diambil
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); //menambahkan autehntikasi token
                };

            }
        } catch (Exception exception) {
            //error
            failureHandler.onAuthenticationFailure(request,response,new BadCredentialsException("Token is invalid"));
            return;
        }

        filterChain.doFilter(request,response);
    }
}
