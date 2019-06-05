package com.codecool.shop.controller;

import com.codecool.shop.model.Order;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/cart"})
public class OrderController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("productId");
        Order order = new Order();


    }
}
