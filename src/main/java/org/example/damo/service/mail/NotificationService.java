package org.example.damo.service.mail;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
@Slf4j

public class NotificationService {

    @Async
    public void sendOrderConfirmationNotification(Long orderId , String text)  {
        String threadName = Thread.currentThread().getName();

        log.info("[ASYNC-NOTIFICATION] Start sending order  notification to Telegram for order: {} | Thread Name: {}", orderId, threadName);

        try {

            //TODO: send notification to Telegram
            Thread.sleep(10000); //10s

            log.info("[ASYNC-NOTIFICATION] Send notification to Telegram successfully for order: {} | Thread Name: {}", orderId, threadName);

        }catch (InterruptedException e){
            log.error("[ASYNC-NOTIFICATION] Failed to send notification to for order: {} | Error: {}", orderId, e.getMessage());
            Thread.currentThread().interrupt();
        }

    }
}
