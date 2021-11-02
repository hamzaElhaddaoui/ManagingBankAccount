package com.bank.managingbankaccount.repository;

import com.bank.managingbankaccount.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByFirstNameAndLastName(String firstName, String lastName);

}