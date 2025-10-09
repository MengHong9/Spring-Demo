package org.example.damo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderDto {
    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    @JsonProperty("items")
    @NotNull(message = "order item is required")
    @NotEmpty(message = "order item can not be empty")
    private List<OrderItemDto> orderItems;
}
