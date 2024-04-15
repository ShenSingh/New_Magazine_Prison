package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.InmateRecord;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InmateRecordRepo {
    public static boolean save(InmateRecord inmateRecord) throws SQLException {

        String query = "INSERT INTO User VALUES(?,?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);

        pstm.setObject(1, inmateRecord.getInmateRecordId());
        pstm.setObject(2, inmateRecord.getInmateId());
        pstm.setObject(3, inmateRecord.getSectionid());
        pstm.setObject(4, inmateRecord.getEntryDate());
        pstm.setObject(5, inmateRecord.getReleaseDate());
        pstm.setObject(6, inmateRecord.getCrime());
        pstm.setObject(7, inmateRecord.getCaseStatus());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String inmateRecordId) throws SQLException {
        String query = "DELETE FROM User WHERE inmateRecordId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);

        pstm.setObject(1, inmateRecordId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(InmateRecord inmateRecord) throws SQLException {
        String query = "UPDATE User SET inmateId = ?, sectionid = ?, entryDate = ?, releaseDate = ?, crime = ?, caseStatus = ? WHERE inmateRecordId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);

        pstm.setObject(1, inmateRecord.getInmateId());
        pstm.setObject(2, inmateRecord.getSectionid());
        pstm.setObject(3, inmateRecord.getEntryDate());
        pstm.setObject(4, inmateRecord.getReleaseDate());
        pstm.setObject(5, inmateRecord.getCrime());
        pstm.setObject(6, inmateRecord.getCaseStatus());
        pstm.setObject(7, inmateRecord.getInmateRecordId());

        return pstm.executeUpdate() > 0;
    }

    public static InmateRecord search(String inmateRecordId) throws SQLException {
        String query = "SELECT * FROM User WHERE inmateRecordId = ?";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);

        pstm.setObject(1, inmateRecordId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String inmateId = resultSet.getString(2);
            String sectionid = resultSet.getString(3);
            String entryDate = resultSet.getString(4);
            String releaseDate = resultSet.getString(5);
            String crime = resultSet.getString(6);
            String caseStatus = resultSet.getString(7);

            InmateRecord inmateRecord = new InmateRecord(id, inmateId, sectionid, entryDate, releaseDate, crime, caseStatus);

            return inmateRecord;
        }

        return null;
    }
}
