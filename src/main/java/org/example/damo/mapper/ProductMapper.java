package org.example.damo.mapper;


import org.example.damo.dto.product.ProductDto;
import org.example.damo.dto.product.ProductResponseDto;
import org.example.damo.entity.Product;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public Product toEntity(ProductDto dto) {
        Product entity = new Product();

        entity.setProductName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public ProductResponseDto toDto(Product entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductId(entity.getId());
        dto.setProductName(entity.getProductName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }

        return entityList.stream()
                .map(product -> this.toDto(product))
                .collect(Collectors.toList());
    }
}
