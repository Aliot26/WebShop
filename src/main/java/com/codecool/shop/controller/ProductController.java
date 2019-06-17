package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.LineItemDaoMem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends MainController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LineItemDaoMem lineItemDaoMem = LineItemDaoMem.getInstance();

        HttpSession session = req.getSession(true);
        Map <String, Object>params = super.getParams();
        params.put("counter", session.getAttribute("totalItems"));
        params.put("productsInCart", lineItemDaoMem.getLineItemList());
        super.renderTemplate(req, resp, "product/index.html", params);
    }
}