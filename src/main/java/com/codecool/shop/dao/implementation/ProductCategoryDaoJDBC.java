package com.codecool.shop.dao.implementation;

import com.codecool.shop.SQL.ConnectionDB;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private static ProductCategoryDaoJDBC instance = null;

    private ProductCategoryDaoJDBC() {

    }

    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        String query = "INSERT INTO categories"
                +"(name, department, description)VALUES "
                + "(?,?,?)";
        try(Connection connect = getConnection();
            PreparedStatement statement = connect.prepareStatement(query)){
            statement.setString(1, category.getName());
            statement.setString(2, category.getDepartment());
            statement.setString(3,category.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        String query = "SELECT * FROM categories WHERE id = ?;";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getProductCategory(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ProductCategory getProductCategory(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            ProductCategory result = new ProductCategory(resultSet.getString("name"),
                    resultSet.getString("department"),
                    resultSet.getString("description"));
            result.setId(resultSet.getInt("id"));
            return result;
        } else {
            return null;
        }
    }


    @Override
    public ProductCategory findByName(String name) {
        String query = "SELECT * FROM categories WHERE name=?";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return getProductCategory(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> listAllCategory = new ArrayList<>();
        String query = "SELECT * FROM categories;";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductCategory result = new ProductCategory(resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description")
                );
                result.setId(resultSet.getInt("id"));
                listAllCategory.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAllCategory;
    }
}
