package com.indocyber.Winterhold;

import com.indocyber.Winterhold.models.MyAccountDetail;
import com.indocyber.Winterhold.repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   private AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account =accountRepository.findById(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        return MyAccountDetail.builder()
                .account(account)
                .build();
    }
}
