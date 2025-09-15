package org.example.damo.dto.stock;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StockDto {

    @NotNull(message = "product ig is required")
    private Long productId;

    private Long supplierId;


    @NotNull(message = "quantity is required")
    @Min(value = 1 , message = "quantity must be at least 1")
    private Integer quantity;


    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
