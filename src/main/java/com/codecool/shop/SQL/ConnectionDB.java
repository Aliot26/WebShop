package com.codecool.shop.SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionDB {
    private static final String DATABASE = System.getenv("database");
    private static final String DB_USER = System.getenv("databaseUser");
    private static final String DB_PASSWORD = System.getenv("databasePassword");
    private static ConnectionDB instance = null;

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

//    public static void main(String[] args) throws SQLException {
//        Connection connection = getConnection();
//        String sql = "SELECT * FROM products";
//
//        if (connection != null) {
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String defaultPrice = rs.getString("defaultPrice");
//                System.out.println(name + " " + defaultPrice);
//            }
//
//            st.close();
//            connection.close();
//        }
//    }

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


    public int executeUpdate(String query, List<Object> parameters) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet res = null;
        int idProduct = 0;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i+1, parameters.get(i));
            }
            statement.execute();
            res = statement.getResultSet();
            if(res.next()){
                idProduct = res.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null) res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return idProduct;
    }

    public List<Map<String, Object>> executeQueryWithReturnValue(String query, List<Object> parameters) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i+1, parameters.get(i));
            }
            statement.executeQuery();
            resultSet = statement.getResultSet();
            int numOfCols = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> data = new HashMap<>();

                for (int i = 0; i < numOfCols; i++) {
                    data.put(resultSet.getMetaData().getColumnName(i+1),
                            resultSet.getObject(i+1));
                }

                resultList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }

}

