package org.example.damo.event;

import lombok.extern.slf4j.Slf4j;
import org.example.damo.event.model.PaymentEvent;
import org.example.damo.repository.OrderRepository;
import org.example.damo.service.kafka.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class PaymentConsumer {
    @Autowired
    private ProducerService<PaymentEvent> producerService;

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "payment.event", groupId = "payment-group")
    public void consume(PaymentEvent event){
        log.info("[PAYMENT_EVENT]Processing order event with payment: {}" , event);

    }
}
