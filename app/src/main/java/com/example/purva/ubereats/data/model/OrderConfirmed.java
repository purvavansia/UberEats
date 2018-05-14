package com.example.purva.ubereats.data.model;

public class OrderConfirmed {
   String orderId;
   String orderName;
   String orderQuantity;
   String totalOrder;
   String orderDeliverAdd;
   String orderDate;

    public OrderConfirmed(String orderId, String orderName, String orderQuantity, String totalOrder, String orderDeliverAdd, String orderDate) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderQuantity = orderQuantity;
        this.totalOrder = totalOrder;
        this.orderDeliverAdd = orderDeliverAdd;
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

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getOrderDeliverAdd() {
        return orderDeliverAdd;
    }

    public void setOrderDeliverAdd(String orderDeliverAdd) {
        this.orderDeliverAdd = orderDeliverAdd;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
