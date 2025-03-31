package com.luntu.banking.service;

import com.luntu.banking.model.WithdrawalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SnsNotificationService implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(SnsNotificationService.class);

    private final SnsClient snsClient;

    @Autowired
    public SnsNotificationService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    @Override
    public void sendNotification(WithdrawalEvent event) {
        String eventJson = event.toJson();
        String snsTopicArn = "arn:aws:sns:YOUR_REGION:YOUR_ACCOUNT_ID:YOUR_TOPIC_NAME";
        logger.info("Sending notification to SNS topic: {}", snsTopicArn);

        PublishRequest publishRequest = PublishRequest.builder()
                .message(eventJson)
                .topicArn(snsTopicArn)
                .build();

        PublishResponse response = snsClient.publish(publishRequest);
        logger.info("Notification sent successfully. Message ID: {}", response.messageId());

    }
}

