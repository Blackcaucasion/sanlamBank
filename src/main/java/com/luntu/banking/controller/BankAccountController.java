package com.luntu.banking.controller;

import com.luntu.banking.exception.InsufficientFundsException;
import com.luntu.banking.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/bank")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);


    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable("accountId") Long accountId,
                                           @RequestParam("amount") BigDecimal amount) {
        logger.info("Withdrawal request: accountId={}, amount={}", accountId, amount);

        try {
            BigDecimal newBalance=   bankAccountService.withdraw(accountId, amount);
            logger.info("Withdrawal successful: accountId={}, newBalance={}", accountId,newBalance);

            return ResponseEntity.ok("Withdrawal successful. New Balance: " + newBalance);
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Withdrawal failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Withdrawal failed");
        }
    }
}
