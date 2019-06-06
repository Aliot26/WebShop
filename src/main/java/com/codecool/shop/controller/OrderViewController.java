package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/orderView"})
public class OrderViewController extends MainController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LineItemDaoMem lineItemDaoMem = LineItemDaoMem.getInstance();

        Map params = super.getParams();
        params.put("itemsInCart", lineItemDaoMem.getLineItemList());
        params.put("totalAmount", lineItemDaoMem.getCartTotalAmount());
        super.renderTemplate(req, resp, "product/checkout1.html", params);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));
        int newQuantity = Integer.parseInt(req.getParameter("newQuantity"));

        LineItemDaoMem lineItemDaoMem = LineItemDaoMem.getInstance();
        lineItemDaoMem.findByProduct(productId).setQuantity(newQuantity);

        Map params = super.getParams();
        params.put("itemsInCart", lineItemDaoMem.getLineItemList());
        params.put("totalAmount", lineItemDaoMem.getCartTotalAmount());

        super.renderTemplate(req, resp, "product/checkout1.html", params);
    }
}
