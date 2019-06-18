package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
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
//    private ProductDao productDataStore = ProductDaoMem.getInstance();
//    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//    private SupplierDao productSupplierDataStore = SupplierDaoMem.getInstance();

    private ProductDao productDataStore = ProductDaoJDBC.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
    private SupplierDao productSupplierDataStore = SupplierDaoJDBC.getInstance();

//    private ProductDao productDataStore;
//    private ProductCategoryDao productCategoryDataStore;
//    private SupplierDao productSupplierDataStore;

    private Map<String, Object> params = createMap();

//    public MainController(){
//        System.out.println("+++++++++");
//        if (System.getenv("datastore").equals("jdbc")) {
//            productDataStore = ProductDaoJDBC.getInstance();
//            productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
//            productSupplierDataStore = SupplierDaoJDBC.getInstance();
//        } else {
//            productDataStore = ProductDaoMem.getInstance();
//            productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//            productSupplierDataStore = SupplierDaoMem.getInstance();
//        }
//    }

    public Map<String, Object> getParams() {
        return params;
    }

    private Map<String, Object> createMap() {

        params = new HashMap<String, Object>();
        params.put("products", productDataStore.getAll());
        params.put("category", productCategoryDataStore.find(0));
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

        context.setVariables(optionalParameters);

        engine.process(HTMLpath, context, resp.getWriter());
    }
}
