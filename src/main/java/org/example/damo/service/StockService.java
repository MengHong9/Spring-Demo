package org.example.damo.service;

import org.example.damo.dto.stock.StockDto;
import org.example.damo.entity.Product;
import org.example.damo.entity.Stock;
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
        Optional<Product> existingProduct = productRepository.findById(stock.getProductId());

        if(existingProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponeModel("fail", "product not found : "+stock.getProductId()));
        }

        Stock stockEntity = stockMapper.toEntity(stock,existingProduct.get());

        stockRepository.save(stockEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponeModel("success" , "successfully created stock"));
    }

    public ResponseEntity<BaseResponseWithAdditionalDateModel> getStockById(Long stockId){
        Optional<Stock> stock = stockRepository.findById(stockId);
        if(stock.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseWithAdditionalDateModel("fail" , "stock not found with id : "+stockId,null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "stock found" , stock.get()));
    }


    public ResponseEntity<BaseResponeModel> adjustQuantity(Long stockId, UpdateStockDto updateStock) {
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
