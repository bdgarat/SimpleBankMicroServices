package com.bdgarat.sbmsnotificationservice.service;

import com.bdgarat.sbmsnotificationservice.event.CreditDisbursementEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class NotificationService {

    private JavaMailSender mailSender;

    public void sendNotification(CreditDisbursementEvent creditDisbursementEvent) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("no-reply@demomailtrap.co");
            mimeMessageHelper.setTo(creditDisbursementEvent.getEmail());
            mimeMessageHelper.setSubject("Notificacion al Disbursement");
            mimeMessageHelper.setText("Dear Customer, your credit disbursement of $" + creditDisbursementEvent.getAmount() + " has been successfully completed");
        };
        try {
            mailSender.send(messagePreparator);
        } catch (Exception e) {
            log.error("Error sending notification: " + e);
            throw new RuntimeException("Error sending notification: " + e);
        }
        log.info("sendNotification creditDisbursementEvent");

    }
}
