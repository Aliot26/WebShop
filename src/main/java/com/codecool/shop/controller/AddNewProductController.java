package com.codecool.shop.controller;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@WebServlet(urlPatterns = {"/add-product"})
public class AddNewProductController extends MainController {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> params = super.getParams();
        super.renderTemplate(req, resp, "product/form.html", params);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        double defaultPrice = Double.parseDouble(req.getParameter("defaultPrice"));
        String currency = req.getParameter("defaultPrice");
        String description = req.getParameter("description");
        String nameCategory = req.getParameter("category");
        String nameSupplier = req.getParameter("supplier");

        ProductCategory category = super.getProductCategoryDataStore().findByName(nameCategory);
        Supplier supplier = super.getProductSupplierDataStore().findByName(nameSupplier);

        Product newProduct = new Product(name, defaultPrice, currency,description, category,supplier);
        Map <String, Object>params = super.getParams();
        params.replace("products", super.getProductDataStore().add(newProduct));
        super.renderTemplate(req, resp, "product/index.html", params);
    }
}
