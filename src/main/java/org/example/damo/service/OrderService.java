package org.example.damo.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.damo.dto.order.OrderDto;
import org.example.damo.dto.order.OrderItemDto;
import org.example.damo.dto.order.OrderUpdateDto;
import org.example.damo.entity.Order;
import org.example.damo.entity.OrderItem;
import org.example.damo.entity.Stock;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.mapper.OrderMapper;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.repository.OrderRepository;
import org.example.damo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockRepository stockRepository;


    public ResponseEntity<BaseResponseWithAdditionalDateModel> listOrders() {
        List<Order> orders = orderRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "successfully" ,  orderMapper.toResponseDtoList(orders)));
    }


    @Transactional
    public ResponseEntity<BaseResponeModel> createOrder(OrderDto payload) {
        // map for product id
        // example: [1,3,2,4]
        List<Long> productIds = payload.getOrderItems()
                .stream()
                .map(OrderItemDto::getProductId)
                .toList();

        // get stock in productIds
        List<Stock> stocks = this.stockRepository.findByProductIdIn(productIds , Sort.by(Sort.Direction.ASC, "createdAt"));


        // map for required quantity of productIds
        // example: 1: 100 , 2: 40
        Map<Long , Integer> requiredQuantities = payload.getOrderItems()
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
                throw new RuntimeException("Not enough stock for product id " + productId);
            }
        }

        stockRepository.saveAll(stocks);


        // create order entity
        Order order = orderMapper.toEntity(payload);
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponeModel("success" , "successfully created order"));
    }


    public ResponseEntity<BaseResponeModel> updateOrderStatus(Long orderId, OrderUpdateDto payload) {
        Order existingOrder = orderRepository.findById(orderId)
                        .orElseThrow(() -> {
                            throw new ResourceNotFoundException("order not found with id : " + orderId);
                        });


        orderMapper.updateEntityFromDto(payload, existingOrder);
        orderRepository.save(existingOrder);


        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success" , "successfully updated order status : " + payload.getStatus()));
    }

    public ResponseEntity<BaseResponeModel> deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("order not found with id : " + orderId);
        }
        orderRepository.deleteById(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success" , "successfully deleted order with id : " + orderId));
    }

}
