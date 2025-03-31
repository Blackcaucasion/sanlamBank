package com.luntu.banking.service;

import com.luntu.banking.model.WithdrawalEvent;

public interface NotificationService {
    void sendNotification(WithdrawalEvent event);
}
