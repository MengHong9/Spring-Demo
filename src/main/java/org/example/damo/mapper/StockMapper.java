package org.example.damo.mapper;


import org.example.damo.dto.stock.StockDto;
import org.example.damo.dto.stock.StockResponseDto;
import org.example.damo.entity.Product;
import org.example.damo.entity.Stock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {

    public Stock toEntity(StockDto dto , Product product) {
        Stock entity = new Stock();

        entity.setProduct(product);
        entity.setQuantity(dto.getQuantity());

        return entity;
    }


    public StockResponseDto toStockResponseDto(Stock entity) {
        StockResponseDto stockResponseDto = new StockResponseDto();

        stockResponseDto.setStockId(entity.getId());
//        stockResponseDto.setProductId(entity.getProduct().getId());
        stockResponseDto.setQuantity(entity.getQuantity());
        stockResponseDto.setCreatedAt(entity.getCreatedAt());
        stockResponseDto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getProduct() != null) {
            stockResponseDto.setProductId(entity.getProduct().getId());
        }

        return stockResponseDto;
    }

    public List<StockResponseDto> toStockResponseDtoList(List<Stock> stocks) {
        if (stocks == null || stocks.isEmpty()) {
            return new ArrayList<>();
        }

        return stocks.stream().map(stock -> this.toStockResponseDto(stock)).collect(Collectors.toList());
    }
}
