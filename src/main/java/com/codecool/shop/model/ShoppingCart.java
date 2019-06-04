package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.ProductDaoMem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private static ShoppingCart instance = null;
    private List<LineItem> cartItemList = new ArrayList<>();

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void addToCart(int id){
        Product product = ProductDaoMem.getInstance().find(id);
        LineItem lineItem = new LineItem(product);
        cartItemList.add(lineItem);

    }
}
