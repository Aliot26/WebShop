package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/category"})
public class CategoriesController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String categoryFromForm = req.getParameter("category");
        prepareCategoryPage(req, resp, categoryFromForm);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // It should work for Get request as well, but I don't know how to send parameters via URL in Jetty :(
        // category=* category/* -> something like this maybe?
        
//        String categoryFromUrl = req.getParameter("category");
//        prepareCategoryPage(req, resp, categoryFromUrl);
    }

    private void prepareCategoryPage(HttpServletRequest req, HttpServletResponse resp, String categoryFromForm) throws IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("category", productCategoryDataStore.findByName(categoryFromForm));
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.findByName(categoryFromForm)));
        context.setVariable("categoryList", productCategoryDataStore.getAll());
        engine.process("product/categories.html", context, resp.getWriter());
    }
}



