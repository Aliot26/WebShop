package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.model.LineItem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/remove"})
public class RemoveController extends MainController  {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));
        LineItemDaoMem lineItemDaoMem = LineItemDaoMem.getInstance();

        LineItem lineToRemove = lineItemDaoMem.findByProduct(productId);

        if(lineToRemove.getQuantity() == 1) {
            lineItemDaoMem.getLineItemList().remove(lineToRemove);
        }
        else {
            lineToRemove.decreaseQuantity();
        }


        Map params = super.getParams();

        int totalItems = lineItemDaoMem.getTotalQuantityInCart();
        HttpSession session = req.getSession(true);
        session.setAttribute("totalItems", totalItems);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariables(params);
        context.setVariable("counter", totalItems);
        context.setVariable("productsInCart", lineItemDaoMem.getLineItemList());
        engine.process("product/index1.html", context, resp.getWriter());
    }
}
