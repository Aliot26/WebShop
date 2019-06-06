package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class LineItemDaoMem implements LineItemDao {

    private List<LineItem> lineItemList = new ArrayList<>();
    private static LineItemDaoMem instance = null;

    public static LineItemDaoMem getInstance() {
        if (instance == null) {
            instance = new LineItemDaoMem();
        }
        return instance;
    }

    @Override
    public void add(LineItem lineItem) {
        this.lineItemList.add(lineItem);
    }

    @Override
    public void remove(LineItem lineItem) {
        this.lineItemList.remove(lineItem);
    }


    public LineItem findByProduct(int productId) {
        return lineItemList.stream().filter(t -> t.getProductId() == productId).findFirst().orElse(null);
    }

    public List<LineItem> getLineItemList() {
        return lineItemList;
    }

    public int getTotalQuantityInCart() {
        int counter = 0;
        for (LineItem lineItem : lineItemList) {
            counter += lineItem.getQuantity();
        }
        return counter;
    }
}
