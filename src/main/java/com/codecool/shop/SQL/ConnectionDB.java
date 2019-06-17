package com.codecool.shop.SQL;

import java.sql.*;

public class ConnectionDB {
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecool_shop";
    private static final String DB_USER = "kacper";
    private static final String DB_PASSWORD = "bubas1993";
    private static ConnectionDB instance = null;

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        String sql = "SELECT * FROM products";

        if (connection != null) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String defaultPrice = rs.getString("defaultPrice");
                System.out.println(name + " " + defaultPrice);
            }

            st.close();
            connection.close();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    DATABASE,
                    DB_USER,
                    DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

