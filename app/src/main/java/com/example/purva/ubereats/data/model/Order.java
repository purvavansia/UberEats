package com.example.purva.ubereats.data.model;

public class Order {
    String orderId;
    String orderName;
    String quantity;
    String totalOrder;
    String orderStatus;
    String orderDeliveryAddr;
    String orderDate;

    public Order(String orderId, String orderName, String quantity, String totalOrder, String orderStatus, String orderDeliveryAddr, String orderDate) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.quantity = quantity;
        this.totalOrder = totalOrder;
        this.orderStatus = orderStatus;
        this.orderDeliveryAddr = orderDeliveryAddr;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDeliveryAddr() {
        return orderDeliveryAddr;
    }

    public void setOrderDeliveryAddr(String orderDeliveryAddr) {
        this.orderDeliveryAddr = orderDeliveryAddr;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
