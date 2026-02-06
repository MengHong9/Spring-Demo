package org.example.damo.service;


import org.example.damo.common.config.ApplicationConfiguration;
import org.example.damo.dto.base.PaginatedResponse;
import org.example.damo.dto.base.Response;
import org.example.damo.dto.stock.StockDto;
import org.example.damo.dto.stock.StockResponseDto;
import org.example.damo.entity.Product;
import org.example.damo.entity.Stock;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.exception.model.UnprocessableEntityException;
import org.example.damo.mapper.StockMapper;

import org.example.damo.dto.stock.UpdateStockDto;
import org.example.damo.repository.ProductRepository;
import org.example.damo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMapper stockMapper;


    @Autowired
    private ApplicationConfiguration appConfig;


    public PaginatedResponse listStockByPagination(Pageable pageable){
        Page<Stock> stocks = stockRepository.findAll(pageable);
        Page<StockResponseDto> stocksPageDtos = stocks.map(stock -> stockMapper.toStockResponseDto(stock));

        return PaginatedResponse.from(stocksPageDtos , appConfig.getPagination().getUrlByResource("stock"));
    }

    public List<StockResponseDto> getStock(){

        List<Stock> stocks = stockRepository.findAll();
        return stockMapper.toStockResponseDtoList(stocks);


    }


    public void createStock(StockDto stock) {
        Product existingProduct = productRepository.findById(stock.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id: " + stock.getProductId()));

        Stock stockEntity = stockMapper.toEntity(stock , existingProduct);

        stockRepository.save(stockEntity);

    }

    public StockResponseDto getStockById(Long stockId){

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id: " + stockId));

        return stockMapper.toStockResponseDto(stock);

    }


    public void adjustQuantity(Long stockId, UpdateStockDto updateStock) {

        //stock not found in DB
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id: " + stockId));





        if (updateStock.getOperationType() == 1){
            int newQuantity = existingStock.getQuantity() + updateStock.getQuantity();
            existingStock.setQuantity(newQuantity);
        } else if (updateStock.getOperationType() == 2) {
            if (existingStock.getQuantity() < updateStock.getQuantity()){
                throw new UnprocessableEntityException("quantity to remove can not be exceeded than existing stock: " + existingStock.getQuantity());
            }
            int newQuantity = existingStock.getQuantity() - updateStock.getQuantity();
            existingStock.setQuantity(newQuantity);
        }else{
            throw new IllegalArgumentException("invalid operation type");
        }

        stockRepository.save(existingStock);

    }


    public void deleteStock(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("stock not found with id: " + stockId);
        }
        stockRepository.deleteById(stockId);
    }
}
