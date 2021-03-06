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
        return Math.round(quantity * product.getDefaultPrice() * 100.0) / 100;
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

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        this.quantity--;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
        else {
            this.quantity = 0;
        }
    }

    public String getProductName() {
        return product.getName();
    }

    public int getProductId() {
        return product.getId();
    }

    public double getProductDefaultPrice() {
        return product.getDefaultPrice();
    }

    public int getQuantity() {
        return quantity;
    }
}
