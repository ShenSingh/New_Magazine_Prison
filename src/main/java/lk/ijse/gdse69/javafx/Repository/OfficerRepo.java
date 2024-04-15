package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Officer;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class OfficerRepo {

    public static boolean save(Officer officer) throws SQLException {
        String query = "INSERT INTO Officer VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, officer.getOfficerId());
        pstm.setObject(2, officer.getOfficerFirstName());
        pstm.setObject(3, officer.getOfficerLastName());
        pstm.setObject(4, officer.getOfficerDOB());
        pstm.setObject(5, officer.getOfficerNIC());
        pstm.setObject(6, officer.getGender());
        pstm.setObject(7, officer.getOfficerAddress());
        pstm.setObject(8, officer.getOfficerEmail());
        pstm.setObject(9, officer.getOfficerNumber());
        pstm.setObject(10, officer.getPosition());
        pstm.setObject(11, officer.getSectionId());
        pstm.setObject(12, officer.getSalary());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String officerId) throws SQLException {
        String query = "DELETE FROM Officer WHERE officerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, officerId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Officer officer) throws SQLException {
        String query = "UPDATE Officer SET officerFirstName = ?, officerLastName = ?, " +
                "officerDOB = ?, officerNIC =?, gender = ?, officerAddress = ?, officerEmail = ?, " +
                "officerNumber = ?, position = ?, sectionId = ?, salary = ? WHERE officerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);

        pstm.setObject(1, officer.getOfficerFirstName());
        pstm.setObject(2, officer.getOfficerLastName());
        pstm.setObject(3, officer.getOfficerDOB());
        pstm.setObject(4, officer.getOfficerNIC());
        pstm.setObject(5, officer.getGender());
        pstm.setObject(6, officer.getOfficerAddress());
        pstm.setObject(7, officer.getOfficerEmail());
        pstm.setObject(8, officer.getOfficerNumber());
        pstm.setObject(9, officer.getPosition());
        pstm.setObject(10, officer.getSectionId());
        pstm.setObject(11, officer.getSalary());
        pstm.setObject(12, officer.getOfficerId());

        return pstm.executeUpdate() > 0;
    }

    public static Officer search(String officerId) throws SQLException {
        String query = "SELECT * FROM Officer WHERE officerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, officerId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String dob = resultSet.getString(4);
            String nic = resultSet.getString(5);
            String gender = resultSet.getString(6);
            String address = resultSet.getString(7);
            String email = resultSet.getString(8);
            String number = resultSet.getString(9);
            String position = resultSet.getString(10);
            String sectionId = resultSet.getString(11);
            double salary = resultSet.getDouble(12);

            Officer officer = new Officer(id, firstName, lastName,dob, nic,gender, address, email, number, position, sectionId, salary);

            return officer;
        }
        return null;
    }
}
