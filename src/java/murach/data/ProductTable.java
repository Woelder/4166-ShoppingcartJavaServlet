package murach.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import shopProj.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductTable {

    static String url = "jdbc:mysql://localhost:3306/phase3";
    static String username = "phase3";
    static String password = "123";

    static Connection connection = null;
    static ResultSet resultset = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

    static {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
    }

    public static List<Product> productList = null;

    public static List<Product> selectProducts() {
        try {
            String preparedSQL = "SELECT * FROM products;";
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            resultset = statement.executeQuery();

            while (resultset.next()) {
                Product product = new Product(resultset.getString("code"),
                        resultset.getString("description"),
                        resultset.getString("price"));
                productList.add(product);
            }

        } catch (SQLException e) {
            System.err.print("Exception in selectProducts @ ProductTable.java");
            System.err.println(e.getMessage());
        }
        return productList;
    }

    public static Product selectProduct(String productCode) {
        Product product = new Product();
        try {
            String preparedSQL = "SELECT code FROM products WHERE code = '" + productCode + "';'";
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            resultset = statement.executeQuery();
            product.setCode(resultset.getString("code"));
            product.setDescription(resultset.getString("description"));
            product.setPrice(resultset.getDouble("price"));
        } catch (SQLException e) {
            System.err.print("Exception in selectProduct @ ProductTable.java");
            System.err.println(e.getMessage());
        }

        return product;
    }

    public static boolean exists(String productCode) {
        boolean found = true;
        try {
            String preparedSQL = "SELECT code FROM products WHERE code = '" + productCode + "';'";
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            resultset = statement.executeQuery();
            if (resultset.getString("code") == "") {
                found = false;
            }

        } catch (SQLException e) {
            System.err.print("Exception in exists @ ProductTable.java");
            System.err.println(e.getMessage());
        }
        return found;
    }

    private static void saveProducts(List<Product> products) {
        try{
        Iterator iterator = products.iterator();
        int looper = 0;
        String preparedSQL = "INSERT INTO products (code,description,price) VALUES";
        PreparedStatement statement = connection.prepareStatement(preparedSQL);
 
        while(iterator.hasNext()){
            Product product = new Product();
            product = products.get(looper);
            preparedSQL += "('"+product.getCode()+"','"+product.getDescription()+"','"+product.getPrice()+"');'";
            
        }
        statement.executeQuery();
        
        }catch (SQLException e){
            System.err.print("Exception in saveProducts @ productTable.java");
            System.err.println(e.getMessage());
        }
        
      
    }

    public static void insertProduct(Product product) {
        try {
            String preparedSQL = "INSERT INTO products (code,description,price) VALUES ('" + product.getCode() + "','"
                    + product.getDescription() + "','" + product.getPrice() + "');'";
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            statement.executeQuery();

        } catch (SQLException e) {
            System.err.print("Exception in insertProduct @ ProductTable.java");
            System.err.println(e.getMessage());
        }
    }

    public static void updateProduct(Product product) {
          try {
            String preparedSQL = "INSERT INTO products (code,description,price) VALUES ('" + product.getCode() + "','"
                    + product.getDescription() + "','" + product.getPrice() + "');'";
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.print("Exception in updateProduct @ ProductTable.java");
            System.err.println(e.getMessage());
        }
    }

    public static void deleteProduct(Product product) {
                 try {
            String preparedSQL = "DELETE FROM products WHERE code='"+product.getCode()+"')'";
            PreparedStatement statement = connection.prepareStatement(preparedSQL);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.print("Exception in deleteProduct @ ProductTable.java");
            System.err.println(e.getMessage());
        }
    }
}
