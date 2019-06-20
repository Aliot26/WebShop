package com.codecool.shop.dao.implementation;

import com.codecool.shop.SQL.ConnectionDB;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;

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
        String query = "INSERT INTO suppliers"
                +"(name,  description)VALUES "
                + "(?,?)";
        try(Connection connect = getConnection();
            PreparedStatement statement = connect.prepareStatement(query)){
            statement.setString(1, supplier.getName());
            statement.setString(2,supplier.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        String query = "SELECT * FROM suppliers WHERE id = ?;";
        try (Connection connect = getConnection();
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
        String query = "DELETE  FROM suppliers WHERE id=?;";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier findByName(String name) {
        String query = "SELECT * FROM suppliers WHERE name=?";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return getSupplier(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> listAllSupplier = new ArrayList<>();
        String query = "SELECT * FROM suppliers;";

        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Supplier result = new Supplier(resultSet.getString("name"),
                        resultSet.getString("description")
                );
                result.setId(resultSet.getInt("id"));
                listAllSupplier.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAllSupplier;
    }
}
