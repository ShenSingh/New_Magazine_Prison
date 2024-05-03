package lk.ijse.gdse69.javafx.Repository;


import lk.ijse.gdse69.javafx.Model.Visitor;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitorRepo {
    public static boolean save(Visitor visitor) throws SQLException {

            try {
                System.out.println("VisitorRepo");
                String sql = "INSERT INTO Visitor VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

                Connection connection = DbConnection.getInstance().getConnection();

                PreparedStatement pstm = connection.prepareStatement(sql);

                pstm.setObject(1, visitor.getVisitorID());
                pstm.setObject(2, visitor.getVisitorFirstName());
                pstm.setObject(3, visitor.getVisitorLastName());
                pstm.setObject(4, visitor.getVisitorDOB());
                pstm.setObject(5, visitor.getVisitorNIC());
                pstm.setObject(6, visitor.getVisitorNumber());
                pstm.setObject(7, visitor.getVisitorAddress());
                pstm.setObject(8, visitor.getVisitorType());
                pstm.setObject(9, visitor.getGender());

                // Execute the update and return true if successful
                return pstm.executeUpdate() > 0;
            } catch (SQLException e) {
                // Handle any SQL exception (e.g., connection error, syntax error)
                e.printStackTrace(); // Print the exception details for debugging
                return false; // Return false to indicate failure
            } catch (Exception e) {
                // Handle any other exception (e.g., unexpected error)
                e.printStackTrace(); // Print the exception details for debugging
                return false; // Return false to indicate failure
            }

    }

    public static boolean delete(String visitorID) throws SQLException {
        String sql = "DELETE FROM visitor WHERE visitorID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, visitorID);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Visitor visitor) throws SQLException {
        String sql = "UPDATE visitor SET visitorFirstName = ?, visitorLastName = ?, visitorDOB = ?, visitorNIC = ?, visitorNumber = ?, visitorAddress = ?, visitorType = ?, gender = ? WHERE visitorID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, visitor.getVisitorFirstName());
        pstm.setObject(2, visitor.getVisitorLastName());
        pstm.setObject(3, visitor.getVisitorDOB());
        pstm.setObject(4, visitor.getVisitorNIC());
        pstm.setObject(5, visitor.getVisitorNumber());
        pstm.setObject(6, visitor.getVisitorAddress());
        pstm.setObject(7, visitor.getVisitorType());
        pstm.setObject(8, visitor.getGender());
        pstm.setObject(9, visitor.getVisitorID());

        return pstm.executeUpdate() > 0;
    }

    public static Visitor search(String visitorID) throws SQLException {
        String sql = "SELECT * FROM Visitor WHERE visitorID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, visitorID);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String visitorId = resultSet.getString(1);
            String visitorFirstName = resultSet.getString(2);
            String visitorLastName = resultSet.getString(3);
            Date visitorDOB = resultSet.getDate(4);
            String visitorNIC = resultSet.getString(5);
            Integer visitorNumber =resultSet.getInt(6);
            String visitorAddress = resultSet.getString(7);
            String visitorType = resultSet.getString(8);
            String gender = resultSet.getString(9);

            Visitor visitor = new Visitor(visitorId, visitorFirstName, visitorLastName, visitorDOB, visitorNIC, visitorNumber, visitorAddress, visitorType,gender);

            return visitor;
        }

        return null;
    }

    public static List<Visitor> getAllVisitors() throws SQLException {

        List<Visitor> allVisitors=new ArrayList<>();

        String sql = "SELECT * FROM Visitor";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String visitorId = resultSet.getString(1);
            String visitorFirstName = resultSet.getString(2);
            String visitorLastName = resultSet.getString(3);
            Date visitorDOB = resultSet.getDate(4);
            String visitorNIC = resultSet.getString(5);
            Integer visitorNumber = resultSet.getInt(6);
            String visitorAddress = resultSet.getString(7);
            String visitorType = resultSet.getString(8);
            String gender = resultSet.getString(9);

            Visitor visitor = new Visitor(visitorId, visitorFirstName, visitorLastName, visitorDOB, visitorNIC, visitorNumber, visitorAddress, visitorType, gender);

           allVisitors.add(visitor);
        }

        return allVisitors;

    }

    public static List<Visitor> getVisitorByVisitorType(String InVisitorType) throws SQLException {
        List<Visitor> allVisitors=new ArrayList<>();

        String sql = "SELECT * FROM Visitor WHERE visitorType = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, InVisitorType);


        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String visitorId = resultSet.getString(1);
            String visitorFirstName = resultSet.getString(2);
            String visitorLastName = resultSet.getString(3);
            Date visitorDOB = resultSet.getDate(4);
            String visitorNIC = resultSet.getString(5);
            Integer visitorNumber = resultSet.getInt(6);
            String visitorAddress = resultSet.getString(7);
            String visitorType = resultSet.getString(8);
            String gender = resultSet.getString(9);

            Visitor visitor = new Visitor(visitorId, visitorFirstName, visitorLastName, visitorDOB, visitorNIC, visitorNumber, visitorAddress, visitorType, gender);

            allVisitors.add(visitor);
        }

        return allVisitors;
    }
}
