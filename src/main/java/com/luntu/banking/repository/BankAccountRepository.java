package com.luntu.banking.repository;

import com.luntu.banking.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/***
 * A Repository interface for Bank Account service
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
