package org.example.damo.controller;


import jakarta.validation.Valid;
import org.example.damo.dto.base.PaginatedResponse;
import org.example.damo.dto.base.Response;
import org.example.damo.dto.product.ProductDto;
import org.example.damo.dto.product.ProductResponseDto;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/paginated")
    public ResponseEntity<Response> ListProductsWithPagination(@PageableDefault(size = 10 , page = 0) Pageable pageable){
        PaginatedResponse<ProductResponseDto> products = productService.getProductWithPagination(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieve product with pagination" , products));
    }

    @GetMapping
    public ResponseEntity<Response> getProducts() {
        return productService.getProduct();
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getOnlyProduct(@PathVariable("id") Long id) {
        return productService.getOneProduct(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Response> getProductsByName(
            @RequestParam(value = "name" , required = false) String name,
            @RequestParam(value = "maxPrice" , required = false) Double maxPrice,
            @RequestParam(value = "minPrice" , required = false) Double minPrice
    ) {
        return productService.searchProduct(name , maxPrice, minPrice);
    }
    @PostMapping
    public ResponseEntity<Response> createProduct(@Valid @RequestBody ProductDto payload) {
        return productService.createProduct(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id, @RequestBody ProductDto payload) {
        return productService.updateProduct(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
