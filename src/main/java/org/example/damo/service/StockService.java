package org.example.damo.service;

import org.example.damo.entity.Stock;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.model.stock.UpdateStockModel;
import org.example.damo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;


    public ResponseEntity<BaseResponseWithAdditionalDateModel> getStock(){
        List<Stock> stocks = stockRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success", "successfully retrieved stocks", stocks));
    }


    public ResponseEntity<BaseResponeModel> createStock(Stock stock) {
        Stock stockEntity = new Stock();

        stockEntity.setProductId(stock.getProductId());
        stockEntity.setQuantity(stock.getQuantity());
        stockEntity.setCreatedAt(LocalDateTime.now());

        stockRepository.save(stockEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponeModel("success" , "successfully created stock"));
    }

    public ResponseEntity<BaseResponeModel> adjustQuantity(Long stockId, UpdateStockModel updateStock) {
        Optional<Stock> existingStock = stockRepository.findById(stockId);

        //stock not found in DB
        if (existingStock.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponeModel("error", "stock not found id : " + stockId));
        }

        Stock stock = existingStock.get();

        if (updateStock.getOperationType() == 1){
            int newQuantity = stock.getQuantity() + updateStock.getQuantity();
            stock.setQuantity(newQuantity);
        } else if (updateStock.getOperationType() == 2) {
            if (stock.getQuantity() < updateStock.getQuantity()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new BaseResponeModel("fail", "quantity to remove can not be exceeded than existing stock"));
            }
            int newQuantity = stock.getQuantity() - updateStock.getQuantity();
            stock.setQuantity(newQuantity);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponeModel("fail", "Invalid operation type "));
        }
        stock.setUpdatedAt(LocalDateTime.now());
        stockRepository.save(stock);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success" , "successfully updated quantity"));
    }


    public ResponseEntity<BaseResponeModel> deleteStock(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponeModel("fail", "stock not found id : " + stockId));
        }
        stockRepository.deleteById(stockId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success", "successfully deleted stock"));
    }
}
