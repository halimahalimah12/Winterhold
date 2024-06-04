package com.indocyber.Winterhold.restAPI.auth.jwt;

import com.indocyber.Winterhold.models.Account;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String generateToken(Account account);
    Boolean isValid(String token, UserDetails userDetails);
    Claims getClaims(String token);
}
