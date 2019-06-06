package com.codecool.shop.controller;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/supplier"})
public class SupplierController extends MainController {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String supplierFromForm = req.getParameter("supplier");

        Map<String, Object> params = super.getParams();

        if (supplierFromForm.equals("All suppliers")) {
            params.replace("products", super.getProductDataStore().getAll());
        } else {
            params.replace("products", super.getProductDataStore().getBy(super.getProductSupplierDataStore().findByName(supplierFromForm)));
        }
        params.replace("supplier", super.getProductSupplierDataStore().findByName(supplierFromForm));

        HttpSession session = req.getSession(true);
        params.put("counter", session.getAttribute("totalItems"));
        super.renderTemplate(req, resp, "product/index1.html", params);
    }
}
