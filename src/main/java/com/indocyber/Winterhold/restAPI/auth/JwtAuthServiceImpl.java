package com.indocyber.Winterhold.restAPI.auth;

import com.indocyber.Winterhold.dtos.auth.AuthTokenRequestDto;
import com.indocyber.Winterhold.dtos.auth.AuthTokenResponseDto;
import com.indocyber.Winterhold.models.MyAccountDetail;
import com.indocyber.Winterhold.repositories.AccountRepository;
import com.indocyber.Winterhold.restAPI.auth.jwt.JwtService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {

    private  final AccountRepository accountRepository;
    private final JwtService jwtService;

    private  final PasswordEncoder passwordEncoder;

    public JwtAuthServiceImpl(AccountRepository accountRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthTokenResponseDto creatToken(AuthTokenRequestDto dto) {
        var account = accountRepository.findById(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("Username od passoword is incorrect");
        }


        return AuthTokenResponseDto.builder()
                .token(jwtService.generateToken(account))
                .build();
    }


}
