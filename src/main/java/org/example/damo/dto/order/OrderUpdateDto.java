package org.example.damo.dto.order;

import org.example.damo.common.annotation.ValidEnum;
import org.example.damo.common.enums.OrderStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class OrderUpdateDto {
    
    @JsonProperty("status")
    @NotBlank(message = "status is required")
    @ValidEnum(enumClass = OrderStatus.class , message = "Value must be one of PENDING,FAILED,SUCCESS")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
 
