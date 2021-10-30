package com.bank.managingbankaccount.repository;

import com.bank.managingbankaccount.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}