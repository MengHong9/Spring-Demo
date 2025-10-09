package org.example.damo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.damo.common.annotation.ValidEnum;
import org.example.damo.common.enums.OrderStatus;

public class OrderUpdateDto {
    @JsonProperty("status")
    @ValidEnum(enumClass = OrderStatus.class , message = "Value must be one or PENDING,FAILED,SUCCESS")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
