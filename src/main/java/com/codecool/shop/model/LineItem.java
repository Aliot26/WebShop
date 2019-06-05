package com.codecool.shop.model;

public class LineItem {
    private Product product;
    private int quantity;

    public LineItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public float calculatePrice() {
        return quantity * product.getDefaultPrice();
    }
}
