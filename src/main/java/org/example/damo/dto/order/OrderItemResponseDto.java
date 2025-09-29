package org.example.damo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItemResponseDto {


    @JsonProperty("product_id")
    private Long productId;

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

    public Long getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @JsonProperty("product_name")
    private String productName;


    @JsonProperty("purchase_amount")
    private Long purchaseAmount;


    @JsonProperty("unit_price")
    private Double unitPrice;

}
