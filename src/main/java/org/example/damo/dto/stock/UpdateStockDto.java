package org.example.damo.dto.stock;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateStockDto {

    @NotNull(message = "operation type is required")
    @Min(value = 1 , message = "operation type must be (1,2)")
    @Max(value = 2 , message = "operation type must be (1,2)")

    private Integer operationType;   // 1 = add , 2 = remove

    @NotNull(message = "quantity is required")
    @Min(value = 1 , message = "quantity must be at least 1")
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }


}
