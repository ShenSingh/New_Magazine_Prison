package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.SetIncidentRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SetIncidentRecordRepo {
    public static boolean save(SetIncidentRecord setIncidentRecord) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/New_Magazine_Prison", "root", "Ijse@123");
        connection.setAutoCommit(false);

        System.out.println(setIncidentRecord.getIncident().getIncidentId());
        System.out.println("saving incident record");
        try {
            boolean isSaved = IncidentRepo.save(setIncidentRecord.getIncident());
            if (isSaved) {
                System.out.println("incident saved");
                boolean isRecordSaved = false;
                System.out.println("saving incident related inmate");

                isRecordSaved = IncidentRelatedInmateRepo.save(setIncidentRecord.getIncident(),setIncidentRecord.getInmateIds());
                
                if (isRecordSaved) {
                    System.out.println("incident related inmate saved");
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
