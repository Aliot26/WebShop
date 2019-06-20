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
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static com.codecool.shop.SQL.ConnectionDB.getConnection;

public class ProductDaoJDBC implements ProductDao {

    private ProductCategoryDaoJDBC productCategoryDataStoreJdbc = ProductCategoryDaoJDBC.getInstance();
    private SupplierDaoJDBC supplierDataStoreJdbc = SupplierDaoJDBC.getInstance();


    private static ProductDaoJDBC instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoJDBC() {
    }

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        String query = "INSERT INTO products"
        +"(name, defaultprice, currency, description, idcategory, idsupplier)VALUES "
                + "(?,?,?,?,?,?)";
        try(Connection connect = getConnection();
        PreparedStatement statement = connect.prepareStatement(query)){
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getDefaultPrice());
            statement.setString(3, String.valueOf(product.getDefaultCurrency()));
            statement.setString(4,product.getDescription());
            statement.setInt(5, product.getProductCategory().getId());
            statement.setInt(6,product.getSupplier().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        String query = "SELECT * FROM products WHERE id=?;";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet;
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product result = new Product(resultSet.getString("name"),
                        Double.parseDouble(resultSet.getString("defaultprice")),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDataStoreJdbc.find(Integer.parseInt(resultSet.getString("idcategory"))),
                        supplierDataStoreJdbc.find(Integer.parseInt(resultSet.getString("idsupplier"))));
                result.setId(resultSet.getInt("id"));
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE  FROM products WHERE id=?;";
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
    public List<Product> getAll() {
        List<Product> allProducts = new ArrayList<>();
        String query = "SELECT * FROM products;";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)
        ) {
            ResultSet resultSet;
            resultSet = statement.executeQuery();
            return getProduct(resultSet, allProducts);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("all products: " + allProducts);
        return allProducts;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        int idSupplier = supplier.getId();
        List<Product> allSupplier = new ArrayList<>();
        return getProductBy(idSupplier, allSupplier);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        int idCategory = productCategory.getId();
        List<Product> allProducts = new ArrayList<>();
        return getProductBy(idCategory, allProducts);
    }

    private List<Product> getProductBy(int idParam, List<Product> allProducts) {
        String query = "SELECT * FROM products WHERE idcategory=?;";
        try (Connection connect = getConnection();
             PreparedStatement statement = connect.prepareStatement(query)
        ) {
            statement.setInt(1, idParam);
            ResultSet resultSet;
            resultSet = statement.executeQuery();
            return getProduct(resultSet, allProducts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Product> getProduct(ResultSet resultSet, List<Product> allProducts) throws SQLException {
        while (resultSet.next()) {
            Product result = new Product(resultSet.getString("name"),
                    Double.parseDouble(resultSet.getString("defaultprice")),
                    resultSet.getString("currency"),
                    resultSet.getString("description"),
                    productCategoryDataStoreJdbc.find(Integer.parseInt(resultSet.getString("idcategory"))),
                    supplierDataStoreJdbc.find(Integer.parseInt(resultSet.getString("idsupplier"))));
            result.setId(resultSet.getInt("id"));

            allProducts.add(result);
        }
        return allProducts;

    }
}
