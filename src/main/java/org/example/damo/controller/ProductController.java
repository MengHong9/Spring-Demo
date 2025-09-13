package org.example.damo.controller;


import org.example.damo.dto.product.ProductDto;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<BaseResponseWithAdditionalDateModel> getProducts() {
        return productService.getProduct();
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponseWithAdditionalDateModel> getOnlyProduct(@PathVariable("id") Long id) {
        return productService.getOneProduct(id);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponseWithAdditionalDateModel> getProductsByName(
            @RequestParam(value = "name" , required = false) String name,
            @RequestParam(value = "maxPrice" , required = false) Double maxPrice,
            @RequestParam(value = "minPrice" , required = false) Double minPrice
    ) {
        return productService.searchProduct(name , maxPrice, minPrice);
    }
    @PostMapping
    public ResponseEntity<BaseResponeModel> createProduct(@RequestBody ProductDto payload) {
        return productService.createProduct(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponeModel> updateProduct(@PathVariable Long id, @RequestBody ProductDto payload) {
        return productService.updateProduct(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponeModel> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}
