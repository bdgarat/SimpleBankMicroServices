package com.bdgarat.sbmscreditdisbursementservice.config;

import com.bdgarat.sbmscreditdisbursementservice.events.CreditDisbursementEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Log4j2
@Service
public class EnqueueDequeService {

    private final AmqpTemplate amqpTemplate;


    @Value("${rabbitmq.exchange}")
    String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    public EnqueueDequeService(RabbitTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publishMessage(CreditDisbursementEvent creditDisbursementEvent){
        amqpTemplate.convertAndSend(exchange,routingkey,creditDisbursementEvent);
        log.info("Credit Disbursement Event - Message sent");
    }
}