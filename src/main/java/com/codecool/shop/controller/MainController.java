package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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
    private Map<String, Object> params = createMap();

    public Map<String, Object> getParams() {
        return params;
    }

    private Map<String, Object> createMap() {

        params = new HashMap<String, Object>();
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

    public void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String HTMLpath, Map<String, Object> optionalParameters) throws IOException  {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariables(params);
        context.setVariables(optionalParameters);

        engine.process(HTMLpath, context, resp.getWriter());
    }
}
