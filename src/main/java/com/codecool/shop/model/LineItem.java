package com.codecool.shop.model;

public class LineItem {
    private Product product;
    private int quantity;
    private int orderId;

    public LineItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public float calculatePrice() {
        return quantity * product.getDefaultPrice();
    }

    public void setOrder(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "LineItem(" +
                "product=" + product +
                ", quantity=" + quantity +
                ", price=" + calculatePrice() +
                ')';
    }
}
