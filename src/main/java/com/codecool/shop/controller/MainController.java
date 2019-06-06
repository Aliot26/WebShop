package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController extends HttpServlet {
    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private SupplierDao productSupplierDataStore = SupplierDaoMem.getInstance();
    private Map params = createMap();

    public Map getParams() {
        return params;
    }

    private Map createMap() {

        params = new HashMap();
        params.put("category", productCategoryDataStore.find(1));
        params.put("products", productDataStore.getAll());
        params.put("categoryList", productCategoryDataStore.getAll());
        params.put("supplierList", productSupplierDataStore.getAll());
        return params;
    }

    public ProductDao getProductDataStore() {
        return productDataStore;
    }

    public ProductCategoryDao getProductCategoryDataStore() {
        return productCategoryDataStore;
    }

    public SupplierDao getProductSupplierDataStore() {
        return productSupplierDataStore;
    }
}
