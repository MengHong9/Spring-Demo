package org.example.damo.mapper;

import org.example.damo.dto.order.*;
import org.example.damo.entity.Order;
import org.example.damo.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    OrderItemMapper orderItemMapper;

    public Order toEntity(OrderDto dto) {
        Order entity = new Order();
        List<OrderItem> orderItemsEntity = dto.getOrderItems()
                .stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
                    orderItem.setOrder(entity);

                    return orderItem;
                })
                .toList();

        entity.setItems(orderItemsEntity);
        return entity;
    }

    public OrderResponseDto toResponseDto(Order entity) {
        if (entity == null) {
            return null;
        }

        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(entity.getId());
        dto.setCreated_at(entity.getCreatedAt());
        dto.setUpdated_at(entity.getUpdatedAt());
        dto.setStatus(entity.getStatus());

        if (entity.getItems() != null && !entity.getItems().isEmpty()) {
            List<OrderItemResponseDto> orderItemDtos = orderItemMapper.toResponseDtoList(entity.getItems());

            dto.setItems(orderItemDtos);


            // map for total price
            Double total = orderItemDtos
                    .stream()
                    .mapToDouble(orderItemResponseDto -> {
                        return orderItemResponseDto.getPurchaseAmount() * orderItemResponseDto.getUnitPrice();
                    } )
                    .sum();

            dto.setTotal(total);
        }

        return dto;
    }


    public  List<OrderResponseDto> toResponseDtoList(List<Order> entityList) {
        return entityList.stream()
                .map(order -> this.toResponseDto(order))
                .toList();
    }


    public void updateEntityFromDto(OrderUpdateDto dto, Order entity) {
        if (dto == null || entity == null) return;

        entity.setStatus(dto.getStatus());
    }

}
