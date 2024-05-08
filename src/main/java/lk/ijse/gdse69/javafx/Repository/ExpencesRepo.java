package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Expences;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpencesRepo {
    public static boolean save(Expences expences) throws SQLException {

        String query = "INSERT INTO Expences VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        if (connection != null && !connection.isClosed()) {
            System.out.println("Connection is active.");
        } else {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
        }
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, expences.getExpenceId());
        pstm.setObject(2, expences.getSectionId());
        pstm.setObject(3, expences.getMonth());
        pstm.setObject(4, expences.getType());
        pstm.setObject(5, expences.getCost());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String expenceId) throws SQLException {
        String query = "DELETE FROM Expences WHERE expencesId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        if (connection != null && !connection.isClosed()) {
            System.out.println("Connection is active.");
        } else {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
        }
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, expenceId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Expences expences) throws SQLException {
        String query = "UPDATE Expences SET sectionId = ?, month = ?, type = ?, cost = ? WHERE expencesId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        if (connection != null && !connection.isClosed()) {
            System.out.println("Connection is active.");
        } else {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
        }
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, expences.getSectionId());
        pstm.setObject(2, expences.getMonth());
        pstm.setObject(3, expences.getType());
        pstm.setObject(4, expences.getCost());
        pstm.setObject(5, expences.getExpenceId());

        return pstm.executeUpdate() > 0;
    }

    public static Expences search(String expenceId) throws SQLException {
        String query = "SELECT * FROM Expences WHERE expencesId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        if (connection != null && !connection.isClosed()) {
            System.out.println("Connection is active.");
        } else {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
        }
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, expenceId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String sectionId = resultSet.getString(2);
            String month = resultSet.getString(3);
            String type = resultSet.getString(4);
            double cost = resultSet.getDouble(5);

            Expences expences = new Expences(id, sectionId, month, type, cost);

            return expences;
        }
        return null;
    }

    public static List<Expences> getAllExpenses() {
        List<Expences> expenses = new ArrayList<>();
        try {
            String query = "SELECT * FROM Expences";

            Connection connection = DbConnection.getInstance().getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection is active.");
            } else {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
            }
            PreparedStatement pstm = connection.prepareStatement(query);

            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String sectionId = resultSet.getString(2);
                String month = resultSet.getString(3);
                String type = resultSet.getString(4);
                double cost = resultSet.getDouble(5);

                Expences expences = new Expences(id, sectionId, month, type, cost);
                expenses.add(expences);
            }
            return expenses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Expences> getExpensesByType(String inType) {
        List<Expences> expenses = new ArrayList<>();
        try {
            String query = "SELECT * FROM Expences WHERE type = ?";

            Connection connection = DbConnection.getInstance().getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection is active.");
            } else {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
            }
            PreparedStatement pstm = connection.prepareStatement(query);

            pstm.setObject(1, inType);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String sectionId = resultSet.getString(2);
                String month = resultSet.getString(3);
                String type = resultSet.getString(4);
                double cost = resultSet.getDouble(5);

                Expences expences = new Expences(id, sectionId, month, type, cost);
                expenses.add(expences);
            }
            return expenses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
