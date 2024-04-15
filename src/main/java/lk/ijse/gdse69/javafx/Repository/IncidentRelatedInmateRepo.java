package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.IncidentRelatedInmate;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncidentRelatedInmateRepo {
    public static boolean save(IncidentRelatedInmate incidentRelatedInmate) throws SQLException{

        String query = "INSERT INTO IncidentRelatedInmate VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, incidentRelatedInmate.getNumber());
        pstm.setObject(2, incidentRelatedInmate.getIncidentID());
        pstm.setObject(3, incidentRelatedInmate.getInmateID());

        return pstm.executeUpdate() > 0;
    }

    /////////////////////////// not set number /////////////////////////////
    public static boolean delete(String number) throws SQLException{
        String query = "DELETE FROM IncidentRelatedInmate WHERE number = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, number);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(IncidentRelatedInmate incidentRelatedInmate) throws SQLException{
        String query = "UPDATE IncidentRelatedInmate SET incidentID = ?, inmateID = ? WHERE number = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, incidentRelatedInmate.getIncidentID());
        pstm.setObject(2, incidentRelatedInmate.getInmateID());
        pstm.setObject(3, incidentRelatedInmate.getNumber());

        return pstm.executeUpdate() > 0;
    }

    public static IncidentRelatedInmate search(int number) throws SQLException{
        String query = "SELECT * FROM IncidentRelatedInmate WHERE number = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, number);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            int num = resultSet.getInt(1);
            String incidentID = resultSet.getString(2);
            String inmateID = resultSet.getString(3);

            IncidentRelatedInmate incidentRelatedInmate = new IncidentRelatedInmate(num, incidentID, inmateID);

            return incidentRelatedInmate;
        }

        return null;
    }
}
