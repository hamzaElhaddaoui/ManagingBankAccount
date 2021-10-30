package com.bank.managingbankaccount.repository;

import com.bank.managingbankaccount.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}