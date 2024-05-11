package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Incident;
import lk.ijse.gdse69.javafx.Model.IncidentRelatedInmate;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidentRelatedInmateRepo {
    public static boolean save(IncidentRelatedInmate incidentRelatedInmate) throws SQLException{

        String query = "INSERT INTO IncidentRelatedInmate VALUES(?,?)";


        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(query);){
            pstm.setObject(1, incidentRelatedInmate.getIncidentID());
            pstm.setObject(2, incidentRelatedInmate.getInmateID());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean save(Incident incident, List<String> inmateIds) {
        String query = "INSERT INTO IncidentRelatedInmate VALUES(?,?)";

        try (Connection connection =DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(query)) {

            for (String inmateId : inmateIds) {
                pstm.setObject(1, incident.getIncidentId());
                pstm.setObject(2, inmateId);

                if (pstm.executeUpdate() <= 0) {
                    return false;
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getInmateIds(String incidentId) {
        List<String> inmateIds = new ArrayList<>();

        String query = "SELECT inmateId FROM IncidentRelatedInmate WHERE incidentId = ?";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setObject(1, incidentId);

            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                inmateIds.add(rst.getString(1));
            }
            System.out.println(inmateIds);
            return inmateIds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
