package com.codecool.shop.dao.implementation;

import com.codecool.shop.SQL.ConnectionDB;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;

public class ProductDaoJDBC implements ProductDao {
    private static final ConnectionDB controller = ConnectionDB.getInstance();
    private ProductCategoryDaoJDBC productCategoryDataStoreJdbc = ProductCategoryDaoJDBC.getInstance();
    private SupplierDaoJDBC supplierDataStoreJdbc = SupplierDaoJDBC.getInstance();
    private static ProductDaoJDBC instance = null;
    private String querySelect = "SELECT products.id, products.name, products.defaultprice," +
            "products.currency, products.description," +
            "categories.id AS cat_id," +
            "categories.name AS cat_name," +
            "categories.department AS cat_dep," +
            "categories.description AS cat_desc," +
            "suppliers.id AS sup_id," +
            "suppliers.name AS sup_name," +
            "suppliers.description AS sup_desc " +
            "FROM products " +
            "JOIN categories " +
            "ON products.idcategory = categories.id " +
            "JOIN suppliers " +
            "ON products.idsupplier = suppliers.id ";

    private ProductDaoJDBC() {
    }

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    private List<Product> createObject(List<Map<String, Object>> resultFromQuery) {
        List<Product> products = new ArrayList<>();

        for (Map singleRow : resultFromQuery) {
            products.add(new Product((Integer) singleRow.get("id"),
                    (String) singleRow.get("name"),
                    (double) singleRow.get("defaultprice"),
                    (String) singleRow.get("currency"),
                    (String) singleRow.get("description"),
                    new ProductCategory((Integer) singleRow.get("cat_id"),
                            (String) singleRow.get("cat_name"),
                            (String) singleRow.get("cat_dep"),
                            (String) singleRow.get("cat_desc")),
                    new Supplier((Integer) singleRow.get("sup_id"),
                            (String) singleRow.get("sup_name"),
                            (String) singleRow.get("sup_desc"))));
        }
        return products;
    }

    @Override
    public void add(Product product) {
        String query = "INSERT INTO products"
                + "(name, defaultprice, currency, description, idcategory, idsupplier)VALUES "
                + "(?,?,?,?,?,?) ON CONFLICT DO NOTHING RETURNING id;";
        int idAddingProduct =
                controller.executeUpdate(query, Arrays.asList(product.getName(),
                        product.getDefaultPrice(), String.valueOf(product.getDefaultCurrency()),
                        product.getDescription(), product.getProductCategory().getId(),
                        product.getSupplier().getId()));
        product.setId(idAddingProduct);
    }

    @Override
    public Product find(int id) {
        String query = querySelect +
                "WHERE products.id=?;";
        List<Map<String, Object>> products;
        products = controller.executeQueryWithReturnValue(query, Collections.singletonList(id));
        return (products.size() != 0) ? this.createObject(products).get(0) : null;
    }

    @Override
    public void remove(int id) {
        controller.executeUpdate(
                "DELETE FROM product WHERE id = ?;",
                Collections.singletonList(id));
    }

    @Override
    public List<Product> getAll() {
        String query = querySelect + ";";
        return this.createObject(controller.executeQueryWithReturnValue(query, Collections.emptyList()));
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        String query = querySelect +
                "WHERE suppliers.id=?;";
        int idSupplier = supplier.getId();
        return this.createObject(controller.executeQueryWithReturnValue(query, Collections.singletonList(idSupplier)));
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = querySelect +
                "WHERE categories.id=?;";
        int idCategory = productCategory.getId();
        return this.createObject(controller.executeQueryWithReturnValue(query, Collections.singletonList(idCategory)));
    }
}
