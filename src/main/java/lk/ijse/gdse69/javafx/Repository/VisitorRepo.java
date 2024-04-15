package lk.ijse.gdse69.javafx.Repository;


import lk.ijse.gdse69.javafx.Model.Visitor;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitorRepo {
    public static boolean save(Visitor visitor) throws SQLException {

        String sql = "INSERT INTO visitor VALUES(?, ?, ?, ?, ?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, visitor.getVisitorID());
        pstm.setObject(2, visitor.getVisitorFirstName());
        pstm.setObject(3, visitor.getVisitorLastName());
        pstm.setObject(4, visitor.getVisitorDOB());
        pstm.setObject(4, visitor.getVisitorNIC());
        pstm.setObject(4, visitor.getVisitorNumber());
        pstm.setObject(5, visitor.getVisitorAddress());
        pstm.setObject(6, visitor.getRelationship());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String visitorID) throws SQLException {
        String sql = "DELETE FROM visitor WHERE visitorID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, visitorID);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Visitor visitor) throws SQLException {
        String sql = "UPDATE visitor SET visitorFirstName = ?, visitorLastName = ?, visitorDOB = ?, visitorNIC = ?, visitorNumber = ?, visitorAddress = ?, relationship = ? WHERE visitorID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, visitor.getVisitorFirstName());
        pstm.setObject(2, visitor.getVisitorLastName());
        pstm.setObject(3, visitor.getVisitorDOB());
        pstm.setObject(4, visitor.getVisitorNIC());
        pstm.setObject(5, visitor.getVisitorNumber());
        pstm.setObject(6, visitor.getVisitorAddress());
        pstm.setObject(7, visitor.getRelationship());
        pstm.setObject(8, visitor.getVisitorID());

        return pstm.executeUpdate() > 0;
    }

    public static Visitor search(String visitorID) throws SQLException {
        String sql = "SELECT * FROM visitor WHERE visitorID = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, visitorID);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String visitorId = resultSet.getString(1);
            String visitorFirstName = resultSet.getString(2);
            String visitorLastName = resultSet.getString(3);
            String visitorDOB = resultSet.getString(4);
            String visitorNIC = resultSet.getString(5);
            String visitorNumber = resultSet.getString(6);
            String visitorAddress = resultSet.getString(7);
            String relationship = resultSet.getString(8);

            Visitor visitor = new Visitor(visitorId, visitorFirstName, visitorLastName, visitorDOB, visitorNIC, visitorNumber, visitorAddress, relationship);

            return visitor;
        }

        return null;
    }
}
