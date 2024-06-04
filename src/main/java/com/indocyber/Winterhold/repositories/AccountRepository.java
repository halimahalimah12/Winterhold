package com.indocyber.Winterhold.repositories;

import com.indocyber.Winterhold.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
