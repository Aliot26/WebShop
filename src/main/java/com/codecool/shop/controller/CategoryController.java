package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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
        Map params = super.getParams();
        params.replace("category", super.getProductCategoryDataStore().findByName(categoryFromForm));
        params.replace("products", super.getProductDataStore().getBy(super.getProductCategoryDataStore().findByName(categoryFromForm)));

        HttpSession session = req.getSession(true);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariables(params);

        context.setVariable("counter", session.getAttribute("totalItems"));
        engine.process("product/index1.html", context, resp.getWriter());
    }
}
