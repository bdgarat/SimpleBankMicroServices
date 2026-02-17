package com.bdgarat.sbmsnotificationservice.config;

import com.bdgarat.sbmsnotificationservice.event.CreditDisbursementEvent;
import com.bdgarat.sbmsnotificationservice.service.NotificationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class MessageListener {

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @NonNull
    private NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleMessage(CreditDisbursementEvent message) {
        log.info("Received message from queue " + queueName);
        notificationService.sendNotification(message);

    }
}