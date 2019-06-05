package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<LineItem> items = new ArrayList<>();
    private Customer customer;
}
