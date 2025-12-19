package org.example.damo.service;

import jakarta.transaction.Transactional;
import org.example.damo.dto.order.OrderDto;

import org.example.damo.dto.order.OrderResponseDto;
import org.example.damo.dto.order.OrderUpdateDto;
import org.example.damo.entity.Order;

import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.mapper.OrderMapper;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;



import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private StockManagementService stockManagementService;


    public List<OrderResponseDto> listOrders() {
        List<Order> orders = orderRepository.findAll();

        return orderMapper.toResponseDtoList(orders);
    }


    @Transactional
    public void createOrder(OrderDto payload) {

        stockManagementService.reserveStockForOrder(payload.getOrderItems());

        // create order entity
        Order order = orderMapper.toEntity(payload);
        orderRepository.save(order);

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
