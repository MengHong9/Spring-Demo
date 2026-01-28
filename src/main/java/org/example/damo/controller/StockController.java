package org.example.damo.controller;


import jakarta.validation.Valid;
import org.example.damo.dto.base.Response;
import org.example.damo.dto.stock.StockDto;
import org.example.damo.dto.stock.StockResponseDto;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.dto.stock.UpdateStockDto;
import org.example.damo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;


    @GetMapping
    public ResponseEntity<Response> listStocks() {
        List<StockResponseDto> dto = stockService.getStock();

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieve stock" , dto));
    }

    @PostMapping
    public ResponseEntity<Response> createStock(@Valid @RequestBody StockDto payload) {
        stockService.createStock(payload);

        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("201" , "success" , "successfully retrieve stock"));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Response> updateStockQuantity(@Valid @RequestBody UpdateStockDto payload, @PathVariable("id") Long stockId) {
        stockService.adjustQuantity(stockId, payload);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("success" , "successfully update stock "));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteStock(@PathVariable("id") Long stockId) {
        stockService.deleteStock(stockId);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("success" , "successfully delete stock "));
    }

    @GetMapping("{stockId}")
    public ResponseEntity<Response> getStock(@PathVariable("stockId") Long stockId) {
        StockResponseDto payload = stockService.getStockById(stockId);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success" , "successfully retrieve stock" , payload));
    }
}
