package com.codecool.shop.dao.implementation;

import com.codecool.shop.SQL.ConnectionDB;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;

public class SupplierDaoJDBC implements SupplierDao {
    private static final ConnectionDB controller = ConnectionDB.getInstance();
    private static SupplierDaoJDBC instance = null;

    private SupplierDaoJDBC() {

    }

    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    private List<Supplier> createObject(List<Map<String, Object>> resultRowsFromQuery) {
        List<Supplier> suppliers = new ArrayList<>();

        for (Map singleRow : resultRowsFromQuery) {
            suppliers.add(new Supplier((Integer) singleRow.get("id"),
                    (String) singleRow.get("name"),
                    (String) singleRow.get("description")));
        }

        return suppliers;
    }

    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO suppliers"
                + "(name, description)VALUES "
                + "(?,?,?)";
        int idAddedSupplier = controller.executeUpdate(query,
                Arrays.asList(supplier.getName(), supplier.getDescription()));
        supplier.setId(idAddedSupplier);
    }

    @Override
    public Supplier find(int id) {
        String query = "SELECT suppliers.id, suppliers.name, " +
                "suppliers.description " +
                "FROM suppliers WHERE id = ?;";
        List<Map<String, Object>> suppliers;
        suppliers = controller.executeQueryWithReturnValue(query, Collections.singletonList(id));

        return (suppliers.size() != 0) ? this.createObject(suppliers).get(0) : null;

    }


    @Override
    public void remove(int id) {
        controller.executeUpdate("DELETE FROM suppliers WHERE id=?",
                Collections.singletonList(id));
    }

    @Override
    public Supplier findByName(String name) {
        String query = "SELECT suppliers.id, suppliers.name, " +
                "suppliers.description " +
                "FROM suppliers WHERE name=?";
        List<Map<String, Object>> suppliers;
        suppliers = controller.executeQueryWithReturnValue(query, Collections.singletonList(name));

        return (suppliers.size() != 0) ? this.createObject(suppliers).get(0) : null;

    }

    @Override
    public List<Supplier> getAll() {
        String query = "SELECT suppliers.id, suppliers.name, " +
                "suppliers.description " +
                "FROM suppliers";
        List<Map<String, Object>> suppliers;
        suppliers = controller.executeQueryWithReturnValue(query, Collections.emptyList());
        return this.createObject(suppliers);

    }
}
