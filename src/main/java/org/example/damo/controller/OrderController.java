package org.example.damo.controller;

import jakarta.validation.Valid;
import org.example.damo.dto.order.OrderDto;
import org.example.damo.dto.order.OrderUpdateDto;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
@Autowired
private OrderService orderService;

    @GetMapping
    public ResponseEntity<BaseResponseWithAdditionalDateModel> getOrders() {
        return orderService.listOrders();
    }

    @PostMapping
    public ResponseEntity<BaseResponeModel> placeOrder(@Valid @RequestBody OrderDto payload) {
        return orderService.createOrder(payload);
    }

    @PatchMapping("/{order_id}")
    public ResponseEntity<BaseResponeModel> updateOrderStatus(@PathVariable("order_id") Long orderId ,@Valid @RequestBody OrderUpdateDto payload) {
        return orderService.updateOrderStatus(orderId , payload);
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<BaseResponeModel> deleteOrder(@PathVariable("order_id") Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
