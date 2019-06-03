package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Customer customer;
    private List<LineItem> lineItemList = new ArrayList<>();

    public List<LineItem> getLineItemList() {
        return lineItemList;
    }

    public void setLineItemList(List<LineItem> lineItemList) {
        this.lineItemList = lineItemList;
    }

    public Order(Customer customer) {
        this.customer = customer;

    }
}
