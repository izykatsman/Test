package DataBase;

import Model.Product;

import java.sql.*;

public class Database {
    public static final String DB_Driver = "org.h2.Driver";
    private static String DB_URL = "jdbc:h2:/";
    private Connection connection;
    private Statement statement;
    public void setDBURL(String url){
        DB_URL += url;
    }
    public Connection getConnection(){
        return  connection;
    }

    public Statement connect() throws ClassNotFoundException, SQLException {
        Class.forName(DB_Driver);
        connection = DriverManager.getConnection(DB_URL);
        statement = connection.createStatement();
        return statement;
    }

    public void closeConnection(){
        try {
            if (connection != null && !connection.isClosed())
                statement.close();
        }
        catch (SQLException e){
            System.out.println("Ошибка закрытия соединения");
        }

    }


    public void creatTable() throws SQLException {
        statement.executeUpdate(
                "CREATE  table IF Not EXISTS product(" +
                "  id INTEGER NOT NULL AUTO_INCREMENT," +
                "  name VARCHAR(50) NOT NULL," +
                "  count INTEGER NOT NULL)"
        );
    }

    public void getAllTable() throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM product");
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("NAME");
            int tmpCount = result.getInt("count");
            System.out.println(id + " " + name + " " + tmpCount);
        }
    }

    public void getByCount(int count) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM product where count > " + count);
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("NAME");
            int tmpCount = result.getInt("count");
            System.out.println(id + " " + name + " " + tmpCount);
        }
    }

    public void setProduct(Product product) throws SQLException {
        if(product != null)
            statement.executeUpdate("Insert INTO product(name, count) " +
                    "VALUES ('" + product.getName() + "'," + product.getCount() + ")");
        else
            throw new NullPointerException();
    }

    public void updateProductCount(int count, int id) throws SQLException {
        statement.executeUpdate("Update product set count = " + count + "where id = " + id);
    }

    public void deleteProduct(int id) throws SQLException {
        statement.executeUpdate("DELETE FROM product where id = " + id);
    }
}
