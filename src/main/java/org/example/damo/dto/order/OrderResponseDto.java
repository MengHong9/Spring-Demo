package org.example.damo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {
    @JsonProperty("order_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    private String status;

    private Double total;

    @JsonProperty("created_at")
    private LocalDateTime created_at;

    public List<OrderItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponseDto> items) {
        this.items = items;
    }

    @JsonProperty("updated_at")
    private LocalDateTime updated_at;

    @JsonProperty("items")
    private List<OrderItemResponseDto> items;
}
