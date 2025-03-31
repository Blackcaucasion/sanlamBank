package com.luntu.banking.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WithdrawalEvent {
    private Long accountId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime timestamp;

    // Constructor
    public WithdrawalEvent(BigDecimal amount, Long accountId, String status) {
        this.accountId = accountId;
        this.amount = amount;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Convert event to JSON for SNS publishing
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting event to JSON", e);
        }
    }

    @Override
    public String toString() {
        return "WithdrawalEvent{" +
                "accountId=" + accountId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
