package org.example.damo.service;

import org.example.damo.dto.stock.StockDto;
import org.example.damo.entity.Product;
import org.example.damo.entity.Stock;
import org.example.damo.entity.Supplier;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.mapper.StockMapper;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.dto.stock.UpdateStockDto;
import org.example.damo.repository.ProductRepository;
import org.example.damo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMapper stockMapper;


    public ResponseEntity<BaseResponseWithAdditionalDateModel> getStock(){
        List<Stock> stocks = stockRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success", "successfully retrieved stocks", stockMapper.toStockResponseDtoList(stocks)));
    }


    public ResponseEntity<BaseResponeModel> createStock(StockDto stock) {
        Product existingProduct = productRepository.findById(stock.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id: " + stock.getProductId()));

        Stock stockEntity = stockMapper.toEntity(stock , existingProduct);

        stockRepository.save(stockEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponeModel("success" , "successfully created stock"));
    }

    public ResponseEntity<BaseResponseWithAdditionalDateModel> getStockById(Long stockId){

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id: " + stockId));

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "stock found" , stockId));
    }


    public ResponseEntity<BaseResponeModel> adjustQuantity(Long stockId, UpdateStockDto updateStock) {

        //stock not found in DB
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id: " + stockId));





        if (updateStock.getOperationType() == 1){
            int newQuantity = existingStock.getQuantity() + updateStock.getQuantity();
            existingStock.setQuantity(newQuantity);
        } else if (updateStock.getOperationType() == 2) {
            if (existingStock.getQuantity() < updateStock.getQuantity()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new BaseResponeModel("fail", "quantity to remove can not be exceeded than existing stock"));
            }
            int newQuantity = existingStock.getQuantity() - updateStock.getQuantity();
            existingStock.setQuantity(newQuantity);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponeModel("fail", "Invalid operation type "));
        }

        stockRepository.save(existingStock);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success" , "successfully updated quantity"));
    }


    public ResponseEntity<BaseResponeModel> deleteStock(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("stock not found with id: " + stockId);
        }
        stockRepository.deleteById(stockId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success", "successfully deleted stock"));
    }
}
