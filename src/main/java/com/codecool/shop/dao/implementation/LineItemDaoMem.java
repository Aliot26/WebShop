package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;

import java.util.ArrayList;
import java.util.List;

public class LineItemDaoMem implements LineItemDao {

    private List<LineItem> lineItemList = new ArrayList<>();

    @Override
    public void add(LineItem lineItem) {

        this.lineItemList.add(lineItem);
        System.out.println(lineItem.toString());
    }
}
