package org.example.damo.mapper;

import org.example.damo.dto.order.OrderItemDto;
import org.example.damo.dto.order.OrderItemResponseDto;
import org.example.damo.entity.OrderItem;
import org.example.damo.entity.Product;
import org.example.damo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemMapper {

    @Autowired
    private ProductRepository productRepository;

    public OrderItem toEntity(OrderItemDto dto) {
        OrderItem entity = new OrderItem();

        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getAmount());


        return entity;
    }

    public OrderItemResponseDto toResponseDto(OrderItem entity) {
        if (entity == null) {
            return null;
        }

        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setProductId(entity.getProductId());
        dto.setPurchaseAmount(entity.getQuantity());



        // query get product
        Product product = productRepository.findById(entity.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + entity.getProductId()));

        dto.setProductName(product.getProductName());
        dto.setUnitPrice(product.getPrice());

        return dto;


    }

    public List<OrderItemResponseDto> toResponseDtoList(List<OrderItem> entityList) {
        return entityList
                .stream()
                .map(orderItem -> this.toResponseDto(orderItem))
                .toList();
    }
}
