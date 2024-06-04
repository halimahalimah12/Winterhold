package com.indocyber.Winterhold.services;

import com.indocyber.Winterhold.UserDetailsServiceImpl;
import com.indocyber.Winterhold.dtos.auth.AuthRegisterDto;
import com.indocyber.Winterhold.models.Account;
import com.indocyber.Winterhold.models.MyAccountDetail;
import com.indocyber.Winterhold.repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService  {
    private final AccountRepository accountRespository;
    public  final PasswordEncoder passwordEncoder;

    public AuthService(AccountRepository accountRespository, PasswordEncoder passwordEncoder) {
        this.accountRespository = accountRespository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(AuthRegisterDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password did not match");
        }

        var hashedPassword = passwordEncoder.encode(dto.getPassword());

        accountRespository.save(Account.builder()
                .username(dto.getUsername().toLowerCase())
                .password(hashedPassword)
                .build());
    }

    public boolean isUSernameExist(String username) {
        return accountRespository.existsById(username.toLowerCase());
    }


}
