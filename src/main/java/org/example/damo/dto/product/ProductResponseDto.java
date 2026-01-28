package org.example.damo.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import java.io.Serializable;
import java.time.LocalDateTime;



@JsonPropertyOrder({"product_id" , "product_name" , "price" , "description" , "created_at" , "updated_at"})
public class ProductResponseDto implements Serializable {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_name")
    private String productName;


    private Double price;
    private String description;

    @JsonProperty("total_stocks")
    private Long totalStocks;


    public Long getTotalStocks() {
        return totalStocks;
    }

    public void setTotalStocks(Long totalStocks) {
        this.totalStocks = totalStocks;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
