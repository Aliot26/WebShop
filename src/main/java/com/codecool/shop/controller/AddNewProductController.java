package com.codecool.shop.controller;

import com.codecool.shop.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/add-product"})
public class AddNewProductController extends MainController {
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Product newProduct = new Product(req.getParameter("name"),
//                req.getParameter("defaultPrice"),
//                req.getParameter("defaultCurrency"),
//                req.getParameter("description"),
//                req.getParameter("category"),
//                req.getParameter("supplier"));
//
//    }
}
