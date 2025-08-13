package org.example.damo.service;

import org.example.damo.entity.Product;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.model.ProductModel;
import org.example.damo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public ResponseEntity<BaseResponseWithAdditionalDateModel> getProduct(){
        List<Product> products = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithAdditionalDateModel("success" , "successfully retrieved products" , products));
    }


    public ResponseEntity<BaseResponeModel> createProduct(@RequestBody ProductModel product) {
        Product productEntity = new Product();

        productEntity.setProductName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setCreatedAt(LocalDateTime.now());

        productRepository.save(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponeModel("success" , "successfully created product"));
    }

    public ResponseEntity<BaseResponeModel> updateProduct(Long id, ProductModel product) {
        Optional<Product> existing = productRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponeModel("fail" , "products not found with id : " + id));
        }

        Product updatedProduct = existing.get();

        updatedProduct.setProductName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setUpdatedAt(LocalDateTime.now());

        productRepository.save(updatedProduct);


        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success", "successfully updated product"));
    }


    public ResponseEntity<BaseResponeModel> deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponeModel("fail" , "products not found with id : " + id));
        }
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success", "successfully deleted product : " +id));
    }

    public ResponseEntity<BaseResponseWithAdditionalDateModel> getOneProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseWithAdditionalDateModel("fail" , "product not found with id : " + id , null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "successfully retrieved product : " +id , product));
    }


    public ResponseEntity<BaseResponseWithAdditionalDateModel> searchProduct(String name) {

        String formatedName = name != null ? name.toLowerCase() : null;
        List<Product> product = productRepository.findProductwithFilter(formatedName);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "successfully retrieved product!" , product));
    }
}
