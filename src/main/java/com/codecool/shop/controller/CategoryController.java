package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/category"})
public class CategoryController extends MainController {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryFromForm = req.getParameter("category");
        Map <String, Object>params = super.getParams();
        if(categoryFromForm.equals("All categories")){
            params.replace("products", super.getProductDataStore().getAll());
        }else{
            params.replace("products", super.getProductDataStore().getBy(super.getProductCategoryDataStore().findByName(categoryFromForm)));
        }
        params.replace("category", super.getProductCategoryDataStore().findByName(categoryFromForm));

        HttpSession session = req.getSession(true);
        params.put("counter", session.getAttribute("totalItems"));

        super.renderTemplate(req, resp, "product/index.html", params);
    }
}
