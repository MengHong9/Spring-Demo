package org.example.damo.dto.stock;


public class UpdateStockDto {
    private Integer operationType;   // 1 = add , 2 = remove

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

    private Integer quantity;
}
