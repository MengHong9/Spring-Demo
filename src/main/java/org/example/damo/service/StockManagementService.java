package org.example.damo.service;

import jakarta.transaction.Transactional;
import org.example.damo.dto.order.OrderItemDto;
import org.example.damo.entity.Stock;
import org.example.damo.exception.model.UnprocessableEntityException;
import org.example.damo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockManagementService {
    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public void reserveStockForOrder(List<OrderItemDto> orderItemDtos) {
        // map for product id
        List<Long> productIds = orderItemDtos
                .stream()
                .map(OrderItemDto::getProductId)
                .toList();

        // get stock in productIds
        List<Stock> stocks = this.stockRepository.findByProductIdIn(productIds , Sort.by(Sort.Direction.ASC, "createdAt"));


        // map for required quantity of productIds
        // example: 1: 100 , 2: 40
        Map<Long , Integer> requiredQuantities = orderItemDtos
                .stream()
                .collect(Collectors.toMap(OrderItemDto::getProductId , OrderItemDto::getAmount));


        // deduct stock for each product
        for(Long productId : requiredQuantities.keySet()) {

            // quantity to deduct
            int remain = requiredQuantities.get(productId);


            // filter stocks by product id
            List<Stock> stocksByProduct = stocks
                    .stream()
                    .filter(stock -> stock.getProduct().getId().equals(productId))
                    .toList();


            // calculate and compare qty
            for(Stock stock : stocksByProduct) {
                if (remain <= 0) break;

                int available = stock.getQuantity();


                if(available >= remain) {
                    stock.setQuantity(available - remain);
                    remain = 0;
                }else {
                    stock.setQuantity(0);
                    remain -= available;
                }
            }

            // not enough qty for sale
            if(remain > 0) {
                throw new UnprocessableEntityException("Not enough stock for product id : " + productId);
            }
        }

        stockRepository.saveAll(stocks);
    }
}
