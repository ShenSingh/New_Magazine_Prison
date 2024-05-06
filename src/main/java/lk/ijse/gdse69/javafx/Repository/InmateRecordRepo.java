package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.InmateRecord;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InmateRecordRepo {
    public static boolean save(InmateRecord inmateRecord) throws SQLException {

        String query = "INSERT INTO InmateRecord (inmateId, sectionId, entryDate, releaseDate, crime, caseStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        System.out.println(inmateRecord.getEntryDate() + " " + inmateRecord.getReleaseDate());

        pstm.setObject(1, inmateRecord.getInmateId());
        pstm.setObject(2, inmateRecord.getSectionId());
        pstm.setObject(3, inmateRecord.getEntryDate());
        pstm.setObject(4, inmateRecord.getReleaseDate());
        pstm.setObject(5, inmateRecord.getCrime());
        pstm.setObject(6, inmateRecord.getCaseStatus());

        return pstm.executeUpdate() > 0;
    }

    public static List<InmateRecord> getRecords(String InInmateId) {
        List<InmateRecord> inmateRecords = new ArrayList<>();

        try {
            String query = "SELECT * FROM InmateRecord WHERE inmateId = ?";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setObject(1, InInmateId);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String InmateId = resultSet.getString(1);
                String sectionId = resultSet.getString(2);
                Date entryDate = resultSet.getDate(3);
                Date releaseDate = resultSet.getDate(4);
                String crime = resultSet.getString(5);
                String caseStatus = resultSet.getString(6);

                InmateRecord inmateRecord = new InmateRecord(InmateId, sectionId, entryDate, releaseDate, crime, caseStatus);

                inmateRecords.add(inmateRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inmateRecords;
    }

    public static List<String> getInmatesIdBySection(String secId) {
        List<String> inmateIds = new ArrayList<>();
        try {
            String query = "SELECT inmateId FROM InmateRecord WHERE sectionId = ?";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setObject(1, secId);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String inmateId = resultSet.getString(1);
                inmateIds.add(inmateId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inmateIds;
    }

//    public static boolean delete(String inmateRecordId) throws SQLException {
//        String query = "DELETE FROM User WHERE inmateRecordId = ?";
//
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        PreparedStatement pstm = connection.prepareStatement(query);
//
//        pstm.setObject(1, inmateRecordId);
//
//        return pstm.executeUpdate() > 0;
//    }

//    public static boolean update(InmateRecord inmateRecord) throws SQLException {
//        String query = "UPDATE User SET inmateId = ?, sectionid = ?, entryDate = ?, releaseDate = ?, crime = ?, caseStatus = ? WHERE inmateRecordId = ?";
//
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        PreparedStatement pstm = connection.prepareStatement(query);
//
//        pstm.setObject(1, inmateRecord.getInmateId());
//        pstm.setObject(2, inmateRecord.getSectionId());
//        pstm.setObject(3, inmateRecord.getEntryDate());
//        pstm.setObject(4, inmateRecord.getReleaseDate());
//        pstm.setObject(5, inmateRecord.getCrime());
//        pstm.setObject(6, inmateRecord.getCaseStatus());
//        pstm.setObject(7, inmateRecord.getInmateRecordId());
//
//        return pstm.executeUpdate() > 0;
//    }

//    public static InmateRecord search(String inmateRecordId) throws SQLException {
//        String query = "SELECT * FROM User WHERE inmateRecordId = ?";
//
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        PreparedStatement pstm = connection.prepareStatement(query);
//
//        pstm.setObject(1, inmateRecordId);
//
//        ResultSet resultSet = pstm.executeQuery();
//
//        if (resultSet.next()) {
//            String id = resultSet.getString(1);
//            String inmateId = resultSet.getString(2);
//            String sectionid = resultSet.getString(3);
//            String entryDate = resultSet.getString(4);
//            String releaseDate = resultSet.getString(5);
//            String crime = resultSet.getString(6);
//            String caseStatus = resultSet.getString(7);
//
//            InmateRecord inmateRecord = new InmateRecord(id, inmateId, sectionid, entryDate, releaseDate, crime, caseStatus);
//
//            return inmateRecord;
//        }
//
//        return null;
//    }
}
