package lk.ijse.gdse69.javafx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;

    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
    }

    public static DbConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        } else {
            System.out.println("connection Closed!");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
        }
        return connection;
    }
}
