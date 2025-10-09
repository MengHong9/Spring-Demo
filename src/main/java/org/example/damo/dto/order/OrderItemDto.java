package org.example.damo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemDto {
    @JsonProperty("product_id")
    @NotNull(message = "product id is required")
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("amount")
    @NotNull(message = "amount is required")
    @Min(value = 1 , message = "amount must be at least 1")
    private Integer amount;
}
