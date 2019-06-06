package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class AddToCartController extends MainController {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int productId = Integer.parseInt(req.getParameter("productId"));
        Product product = super.getProductDataStore().find(productId);
        LineItemDaoMem lineItemDaoMem = LineItemDaoMem.getInstance();

        if (lineItemDaoMem.findByProduct(productId) == null) {
            LineItem lineItem = new LineItem(product);
            lineItemDaoMem.add(lineItem);
        } else {
            lineItemDaoMem.findByProduct(productId).increaseQuantity();
        }

        int totalItems = lineItemDaoMem.getTotalQuantityInCart();

        Map params = super.getParams();
        HttpSession session = req.getSession(true);
        session.setAttribute("totalItems", totalItems);

        params.put("counter", totalItems);
        params.put("productsInCart", lineItemDaoMem.getLineItemList());

        super.renderTemplate(req, resp, "product/index1.html", params);
    }
}
