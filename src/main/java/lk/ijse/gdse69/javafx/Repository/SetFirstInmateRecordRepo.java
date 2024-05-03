package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.SetFirstInmateRecord;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class SetFirstInmateRecordRepo {
    public static boolean setFirstInmateRecord(SetFirstInmateRecord setFirstInmateRecord) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isSaved = InmateRepo.save(setFirstInmateRecord.getInmate());
            if (isSaved) {
                boolean isRecordSaved = InmateRecordRepo.save(setFirstInmateRecord.getInmateRecord());
                if (isRecordSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
