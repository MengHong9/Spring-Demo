package org.example.damo.service.mail;


import lombok.extern.slf4j.Slf4j;
import org.example.damo.common.config.ApplicationConfiguration;
import org.example.damo.common.wrapper.WebClientWrapper;
import org.example.damo.entity.Order;
import org.example.damo.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j

public class NotificationService {

    @Autowired
    private WebClientWrapper  webClientWrapper;

    @Autowired
    private ApplicationConfiguration  applicationConfiguration;


    @Async
    public void sendOrderConfirmationNotification(Order order)  {
        String threadName = Thread.currentThread().getName();

        log.info("[ASYNC-NOTIFICATION] Start sending order  notification to Telegram for order: {} | Thread Name: {}", order.getId(), threadName);

        try {

            String formattedMessage = this.generateTelegramNotificationTemplate(order);
            this.sentNotification(formattedMessage);

            log.info("[ASYNC-NOTIFICATION] Send notification to Telegram successfully for order: {} | Thread Name: {}", order.getId(), threadName);

        }catch (Exception e){
            log.error("[ASYNC-NOTIFICATION] Failed to send notification to for order: {} | Error: {}", order.getId(), e.getMessage());

        }

    }


    private void sentNotification(String content){

        String url = applicationConfiguration.getTelegram().getBaseUrl();
        String chatId = applicationConfiguration.getTelegram().getChatId();

        Map<String , String> payload = new HashMap<>();
        payload.put("text", content);
        payload.put("chat_id", chatId);
        payload.put("parse_mode", "HTML");

        webClientWrapper.postSync(url , payload , Object.class);
    }


    private String generateTelegramNotificationTemplate(Order order){
        StringBuilder message = new StringBuilder();
        int itemCount = order.getItems() != null ? order.getItems().size() : 0;

        message.append("<b>💌New order received</b>\n");
        message.append("Order ID: #").append(order.getId()).append("\n");
        message.append("Order status: ").append(order.getStatus()).append("\n");
        message.append("Order at: ").append(order.getCreatedAt()).append("\n");
        message.append("Items count: ").append(itemCount).append("\n");


        if (itemCount != 0) {
            message.append("\n<b>Order Detail: </b>\n");

            for (OrderItem item : order.getItems()) {
                message.append(" -Product ID: ").append(item.getProductId())
                        .append(" (Qty: ").append(item.getQuantity()).append(")\n");
            }
        }else {
            message.append("<i>No Item</i>");
        }

        message.append("<b>Action required: check the order process</b>\n");
        return message.toString();
    }

}
