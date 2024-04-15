package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Section;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionRepo {
    public static boolean save(Section section) throws SQLException {
        String query = "INSERT INTO Section VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, section.getSectionId());
        pstm.setObject(2, section.getSectionId());
        pstm.setObject(3, section.getLocation());
        pstm.setObject(4, section.getCapacity());
        pstm.setObject(5, section.getSecurityLevel());

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
        String query = "UPDATE Section SET sectionName = ?, location = ?, capacity = ?, securityLevel = ? WHERE sectionId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, section.getSectionName());
        pstm.setObject(1, section.getLocation());
        pstm.setObject(2, section.getCapacity());
        pstm.setObject(3, section.getSecurityLevel());
        pstm.setObject(4, section.getSectionId());

        return pstm.executeUpdate() > 0;
    }

    public static Section search(String sectionId) throws SQLException {
        String query = "SELECT * FROM Section WHERE sectionId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, sectionId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String sectionName = resultSet.getString(1);
            String location = resultSet.getString(2);
            String capacity = resultSet.getString(3);
            String securityLevel = resultSet.getString(4);

            Section section = new Section(sectionId, sectionName, location, capacity, securityLevel);

            return section;
        }

        return null;
    }


}
