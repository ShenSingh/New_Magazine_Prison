package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.SetIncidentRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SetIncidentRecordRepo {
    public static boolean save(SetIncidentRecord setIncidentRecord) throws SQLException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
            connection.setAutoCommit(false);
            boolean isSaved = IncidentRepo.save(setIncidentRecord.getIncident());
            if (isSaved) {
                boolean isRecordSaved = false;
                isRecordSaved = IncidentRelatedInmateRepo.save(setIncidentRecord.getIncident(),setIncidentRecord.getInmateIds());
                if (isRecordSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
