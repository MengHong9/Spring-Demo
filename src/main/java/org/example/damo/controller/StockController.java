package org.example.damo.controller;

import org.example.damo.entity.Stock;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.model.stock.UpdateStockModel;
import org.example.damo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;


    @GetMapping
    public ResponseEntity<BaseResponseWithAdditionalDateModel> listStocks() {
        return stockService.getStock();
    }

    @PostMapping
    public ResponseEntity<BaseResponeModel> createStock(@RequestBody Stock payload) {
        return stockService.createStock(payload);
    }

    @PatchMapping("{id}")
    public ResponseEntity<BaseResponeModel> updateStockQuantity(@RequestBody UpdateStockModel payload, @PathVariable("id") Long stockId) {
        return stockService.adjustQuantity(stockId, payload);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponeModel> deleteStock(@PathVariable("id") Long stockId) {
        return stockService.deleteStock(stockId);
    }
}
