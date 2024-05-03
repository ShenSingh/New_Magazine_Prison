package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionRepo {
    public static boolean save(Section section) throws SQLException {
        String query = "INSERT INTO Section VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, section.getSectionId());
        pstm.setObject(2, section.getSectionName());
        pstm.setObject(3, section.getLocation());
        pstm.setObject(4, section.getCapacity());
        pstm.setObject(5, section.getSecurityLevel());
        pstm.setObject(6,section.getStatus());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String sectionId) throws SQLException {
        String query = "DELETE FROM Section WHERE sectionId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, sectionId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Section section) throws SQLException {
        String query = "UPDATE Section SET sectionName = ?, location = ?, capacity = ?, securityLevel = ?, status = ? WHERE sectionId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, section.getSectionName());
        pstm.setObject(2, section.getLocation());
        pstm.setObject(3, section.getCapacity());
        pstm.setObject(4, section.getSecurityLevel());
        pstm.setObject(5, section.getStatus());
        pstm.setObject(6, section.getSectionId());

        return pstm.executeUpdate() > 0;
    }

    public static Section search(String sectionId) throws SQLException {
        String query = "SELECT * FROM Section WHERE sectionId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, sectionId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String sectionName = resultSet.getString(2);
            String location = resultSet.getString(3);
            Integer capacity = Integer.valueOf(resultSet.getString(4));
            String securityLevel = resultSet.getString(5);
            String status = resultSet.getString(6);

            Section section = new Section(sectionId, sectionName, location, capacity, securityLevel, status);

            return section;
        }

        return null;
    }


    public static List<Section> getAllSections() throws SQLException {
        List<Section> sectionList = null;
        String query = "SELECT * FROM Section";

        Connection connection = DbConnection.getInstance().getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String sectionId = resultSet.getString(1);
                String sectionName = resultSet.getString(2);
                String location = resultSet.getString(3);
                Integer capacity = Integer.valueOf(resultSet.getString(4));
                String securityLevel = resultSet.getString(5);
                String status = resultSet.getString(6);

                Section section = new Section(sectionId, sectionName, location, capacity, securityLevel, status);
                sectionList.add(section);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sectionList;
    }

    public static List<Section> getJailSections() throws SQLException {
        List<Section> sectionList = new ArrayList<>();

        String query = "SELECT * FROM Section WHERE sectionName LIKE 'Jail-%'";

        Connection connection = DbConnection.getInstance().getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String sectionId = resultSet.getString(1);
                String sectionName = resultSet.getString(2);
                String location = resultSet.getString(3);
                Integer capacity = resultSet.getInt(4);
                String securityLevel = resultSet.getString(5);
                String status = resultSet.getString(6);

                Section section = new Section(sectionId, sectionName, location, capacity, securityLevel, status);
                sectionList.add(section);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sectionList;

    }
}
