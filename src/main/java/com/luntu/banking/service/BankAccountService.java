package com.luntu.banking.service;

import com.luntu.banking.exception.AccountNotFoundException;
import com.luntu.banking.exception.InsufficientFundsException;
import com.luntu.banking.model.BankAccount;
import com.luntu.banking.model.WithdrawalEvent;
import com.luntu.banking.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final NotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);


    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository,
                              NotificationService notificationService) {
        this.bankAccountRepository = bankAccountRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public BigDecimal  withdraw(Long accountId, BigDecimal amount) {
        logger.info("Processing withdrawal of {} for account {}", amount, accountId);

        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            logger.warn("Insufficient funds in account {}. Balance: {}, Withdrawal amount: {}",
                    accountId, account.getBalance(), amount);
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }


        BigDecimal newBalance =  account.withdraw(amount);
        account.setBalance(newBalance);
        bankAccountRepository.save(account);

        // Notify event after successful withdrawal
//        WithdrawalEvent event = new WithdrawalEvent(amount, accountId, "SUCCESSFUL");
//        notificationService.sendNotification(event);
        logger.info("Withdrawal successful. New balance: {}", account.getBalance());
        return newBalance;
    }
}

