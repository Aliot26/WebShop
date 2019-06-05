package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class OrderController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        Product product = productDataStore.find(productId);
        LineItemDaoMem lineItemDaoMem = LineItemDaoMem.getInstance();

        if(lineItemDaoMem.findByProduct(productId) == null) {
            LineItem lineItem = new LineItem(product);
            lineItemDaoMem.add(lineItem);
        }
        else {
            lineItemDaoMem.findByProduct(productId).increaseQuantity();
        }

        int totalItems = lineItemDaoMem.getTotalQuantityInCart();

        HttpSession session = req.getSession(true);
        session.setAttribute("totalItems", totalItems);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("products", productDataStore.getAll());
        context.setVariable("categoryList", productCategoryDataStore.getAll());
        context.setVariable("counter", totalItems);
        context.setVariable("productsInCart",lineItemDaoMem.getLineItemList());
        System.out.println(lineItemDaoMem.getLineItemList());
        engine.process("product/index1.html", context, resp.getWriter());
    }
}
