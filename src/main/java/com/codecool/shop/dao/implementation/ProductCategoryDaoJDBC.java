package com.codecool.shop.dao.implementation;

import com.codecool.shop.SQL.ConnectionDB;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private static final ConnectionDB controller = ConnectionDB.getInstance();
    private static ProductCategoryDaoJDBC instance = null;

    private ProductCategoryDaoJDBC() {

    }

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    private List<ProductCategory> createObject(List<Map<String, Object>> resultRowsFromQuery) {
        List<ProductCategory> productCategories = new ArrayList<>();

        for (Map singleRow : resultRowsFromQuery) {
            productCategories.add(new ProductCategory((Integer) singleRow.get("id"),
                    (String) singleRow.get("name"),
                    (String) singleRow.get("description"),
                    (String) singleRow.get("department")));
        }

        return productCategories;
    }

    @Override
    public void add(ProductCategory category) {
        String query = "INSERT INTO categories"
                + "(name, department, description)VALUES "
                + "(?,?,?)ON CONFLICT DO NOTHING RETURNING id;";
        int idAddedCategory = controller.executeUpdate(query, Arrays.asList(category.getName(),
                category.getDepartment(), category.getDescription()));
        category.setId(idAddedCategory);
    }

    @Override
    public ProductCategory find(int id) {
        String query = "SELECT categories.id, categories.name, " +
                "categories.department, categories.description " +
                "FROM categories WHERE id = ?;";
        List<Map<String, Object>> categories;
        categories = controller.executeQueryWithReturnValue(query, Collections.singletonList(id));

        return(categories.size() != 0) ? this.createObject(categories).get(0) : null;
    }


    @Override
    public ProductCategory findByName(String name) {
        String query = "SELECT categories.id, categories.name, " +
                "categories.department, categories.description " +
                "FROM categories WHERE name=?";
        List<Map<String, Object>> categories;
        categories = controller.executeQueryWithReturnValue(query, Collections.singletonList(name));

        return(categories.size() != 0) ? this.createObject(categories).get(0) : null;
    }

    @Override
    public void remove(int id) {
        controller.executeUpdate("DELETE FROM categories WHERE id=? RETURNING id;",
                Collections.singletonList(id));

    }

    @Override
    public List<ProductCategory> getAll() {
        String query = "SELECT categories.id, categories.name, " +
                "categories.department, categories.description " +
                "FROM categories;";
        List<Map<String, Object>> categories;
        categories = controller.executeQueryWithReturnValue(query, Collections.emptyList());
        return this.createObject(categories);
    }
}
