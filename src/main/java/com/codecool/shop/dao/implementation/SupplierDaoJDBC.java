package com.codecool.shop.dao.implementation;

import com.codecool.shop.SQL.ConnectionDB;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {

    private static SupplierDaoJDBC instance = null;

    private SupplierDaoJDBC() {

    }

    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        String query = "SELECT * FROM suppliers WHERE id = ?;";
        try (Connection connect = ConnectionDB.getConnection();
             PreparedStatement statement = connect.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getSupplier(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Supplier getSupplier(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Supplier result = new Supplier(resultSet.getString("name"),
                    resultSet.getString("description"));
            result.setId(resultSet.getInt("id"));
            return result;
        } else {
            return null;
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Supplier findByName(String name) {
        return null;
    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }
}
