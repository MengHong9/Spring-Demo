package org.example.damo.service;

import org.example.damo.common.config.ApplicationConfiguration;
import org.example.damo.dto.base.PaginatedResponse;
import org.example.damo.dto.base.Response;
import org.example.damo.dto.product.ProductDto;
import org.example.damo.dto.product.ProductResponseDto;
import org.example.damo.entity.Product;
import org.example.damo.exception.model.DuplicateResourceException;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.mapper.ProductMapper;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ApplicationConfiguration appConfig;


    @Cacheable(value = "products-paginated" , key = "T(String).valueOf(#pageable.getPageNumber()).concat('-').concat(T(String).valueOf(#pageable.getPageSize()))")
    public PaginatedResponse getProductWithPagination(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        Page<ProductResponseDto> productPageDto = productPage.map(product -> productMapper.toDto(product));

        return PaginatedResponse.from(productPageDto , appConfig.getPagination().getUrlByResource("product"));
    }


    @Cacheable(value = "products" , key = "'all'")
    public List<ProductResponseDto> getProduct(){
        List<Product> products = productRepository.findAll();

        return productMapper.toDtoList(products);
    }



    @CachePut(value = "products" , key = "#product.getName()")
    public void createProduct(ProductDto product) {

        if(productRepository.existsByProductName(product.getName())) {
            throw new DuplicateResourceException("product already exists");
        }

        Product productEntity = productMapper.toEntity(product);


        productRepository.save(productEntity);

    }


    @CacheEvict(value = "products" , key = "#id")
    public void updateProduct(Long id, ProductDto product) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id " + id));


        existing.setProductName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());


        productRepository.save(existing);


    }


    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("product not found with id " + id);
        }
        productRepository.deleteById(id);
    }


    @Cacheable(value = "products" , key = "#id")
    public ProductResponseDto getOneProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id " + id));


        return productMapper.toDto(product);
    }


    public ResponseEntity<Response> searchProduct(String name , Double maxPrice , Double  minPrice) {

        String formatedName = name != null ? name.toLowerCase() : null;
        List<Product> product = productRepository.findProductWithFilter(formatedName , maxPrice , minPrice);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success" , "successfully retrieved product!" , product));
    }
}
