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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/paginated")
    public ResponseEntity<Response> ListProductsWithPagination(@PageableDefault(size = 10 , page = 0 , sort = "id" , direction = Sort.Direction.DESC) Pageable pageable){
        PaginatedResponse<ProductResponseDto> products = productService.getProductWithPagination(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieve product with pagination" , products));
    }

    @GetMapping
    public ResponseEntity<Response> getProducts() {
        List<ProductResponseDto> products = productService.getProduct();
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieve product" , products));
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getOnlyProduct(@PathVariable("id") Long id) {
        ProductResponseDto dto = productService.getOneProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieve product " , dto));
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
        productService.createProduct(payload);

        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("201" , "success" , "successfully add product "));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id, @RequestBody ProductDto payload) {
        productService.updateProduct(id, payload);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success( "success" , "successfully update product "));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("success" , "successfully delete product "));
    }
}
