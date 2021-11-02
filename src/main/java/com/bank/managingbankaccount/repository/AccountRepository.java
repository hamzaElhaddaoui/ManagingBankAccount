package com.bank.managingbankaccount.repository;

import com.bank.managingbankaccount.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByName(String name);

}