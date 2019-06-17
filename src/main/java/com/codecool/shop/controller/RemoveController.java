package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.model.LineItem;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/remove"})
public class RemoveController extends MainController  {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LineItemDaoMem lineItemDaoMem = LineItemDaoMem.getInstance();
        int totalItems;
        HttpSession session = req.getSession(true);

        Map <String, Object>params = super.getParams();

        if(req.getParameter("productId") != null) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            LineItem lineToRemove = lineItemDaoMem.findByProduct(productId);

            if(lineToRemove.getQuantity() == 1) {
                lineItemDaoMem.getLineItemList().remove(lineToRemove);
            }
            else {
                lineToRemove.decreaseQuantity();
            }
            totalItems = lineItemDaoMem.getTotalQuantityInCart();
            session.setAttribute("totalItems", lineItemDaoMem.getTotalQuantityInCart());

            params.put("counter", totalItems);
            params.put("productsInCart", lineItemDaoMem.getLineItemList());
            super.renderTemplate(req, resp, "product/index.html", params);
        }

        else if(req.getParameter("productIdFromOrder") != null) {
            int productId = Integer.parseInt(req.getParameter("productIdFromOrder"));
            LineItem lineToRemove = lineItemDaoMem.findByProduct(productId);
            lineItemDaoMem.getLineItemList().remove(lineToRemove);

            totalItems = lineItemDaoMem.getTotalQuantityInCart();
            session.setAttribute("totalItems", lineItemDaoMem.getTotalQuantityInCart());

            params.put("counter", totalItems);
            params.put("itemsInCart", lineItemDaoMem.getLineItemList());
            super.renderTemplate(req, resp, "product/checkout.html", params);
        }
    }
}
