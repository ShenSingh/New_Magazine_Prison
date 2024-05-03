package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.SetFirstVisitorRecord;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class SetFirstVisitorRecordRepo {
    public static boolean save(SetFirstVisitorRecord setFirstVisitorRecord) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        boolean isAllSaved = false;

        System.out.println(setFirstVisitorRecord.getVisitor().getVisitorID());
        System.out.println(setFirstVisitorRecord.getVisitorRecord().getVisitorRecordId());

        try {
            System.out.println("Visitor Record Saving");
            boolean isSaved = VisitorRepo.save(setFirstVisitorRecord.getVisitor());
            if (isSaved) {
                System.out.println("Visitor Saved");
                boolean isRecordSaved = VisitorRecordRepo.save(setFirstVisitorRecord.getVisitorRecord());
                if (isRecordSaved) {
                    System.out.println("Visitor Record Saved");
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            isAllSaved = false;
        } catch (Exception e) {
            isAllSaved = false;
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
}
