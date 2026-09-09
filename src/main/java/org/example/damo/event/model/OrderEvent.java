package org.example.damo.event.model;

/** Event published after an order has been persisted. */
public class OrderEvent {
    private Long orderId;

    public OrderEvent() {
    }

    public OrderEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
