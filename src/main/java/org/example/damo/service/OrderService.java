package org.example.damo.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.damo.dto.order.OrderDto;

import org.example.damo.dto.order.OrderResponseDto;
import org.example.damo.dto.order.OrderUpdateDto;
import org.example.damo.entity.Order;

import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.mapper.OrderMapper;
import org.example.damo.repository.OrderRepository;

import org.example.damo.service.mail.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;



import java.util.List;


@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private StockManagementService stockManagementService;

    @Autowired
    private NotificationService notificationService;


    public List<OrderResponseDto> listOrders() {
        List<Order> orders = orderRepository.findAll();

        return orderMapper.toResponseDtoList(orders);
    }


    @Transactional
    public void createOrder(OrderDto payload) {

        String threadName = Thread.currentThread().getName();

        log.info("[SYNC-ORDER] Creating order | Thread Name: {}", threadName);
        stockManagementService.reserveStockForOrder(payload.getOrderItems());

        // create order entity
        Order order = orderMapper.toEntity(payload);
        orderRepository.save(order);

        log.info("[SYNC-ORDER] Order created successfully with order: {} | Thread Name: {}", order.getId(), threadName);
        log.info("[SYNC-ORDER] Trigger send notification asynchronously for order: {} | Thread Name: {}", order.getId(), threadName );


        notificationService.sendOrderConfirmationNotification(order.getId(),"Your order has been created");
        log.info("[SYNC-ORDER] Completed order and triggered send notification");
    }


    public OrderResponseDto updateOrderStatus(Long orderId, OrderUpdateDto payload) {
        Order existingOrder = orderRepository.findById(orderId)
                        .orElseThrow(() -> {
                            throw new ResourceNotFoundException("order not found with id : " + orderId);
                        });


        orderMapper.updateEntityFromDto(payload, existingOrder);
        orderRepository.save(existingOrder);


        return orderMapper.toResponseDto(existingOrder);
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("order not found with id : " + orderId);
        }
        orderRepository.deleteById(orderId);

    }

}
