package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.VisitorRecord;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitorRecordRepo {
    public static boolean save(VisitorRecord visitorRecord) throws SQLException {

        String query = "INSERT INTO VisitorRecord VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);

        pstm.setObject(1, visitorRecord.getVisitorRecordId());
        pstm.setObject(2, visitorRecord.getVisitorId());
        pstm.setObject(3, visitorRecord.getInmateId());
        pstm.setObject(4, visitorRecord.getVisitDate());
        pstm.setObject(5, visitorRecord.getVisitTime());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String visitorRecordId) throws SQLException {
        String query = "DELETE FROM VisitorRecord WHERE visitorRecordId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, visitorRecordId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(VisitorRecord visitorRecord) throws SQLException {
        String query = "UPDATE VisitorRecord SET visitorId = ?, inmateId = ?, visitDate = ?, visitTime = ? WHERE visitorRecordId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, visitorRecord.getVisitorId());
        pstm.setObject(2, visitorRecord.getInmateId());
        pstm.setObject(3, visitorRecord.getVisitDate());
        pstm.setObject(4, visitorRecord.getVisitTime());
        pstm.setObject(5, visitorRecord.getVisitorRecordId());

        return pstm.executeUpdate() > 0;
    }

    public static VisitorRecord search(String visitorRecordId) throws SQLException {
        String query = "SELECT * FROM VisitorRecord WHERE visitorRecordId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, visitorRecordId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String visitorRecordId1 = resultSet.getString(1);
            String visitorId = resultSet.getString(2);
            String inmateId = resultSet.getString(3);
            String visitDate = resultSet.getString(4);
            String visitTime = resultSet.getString(5);

            VisitorRecord visitorRecord = new VisitorRecord(visitorRecordId1, visitorId, inmateId, visitDate, visitTime);

            return visitorRecord;
        }
        return null;
    }
}
