package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Incident;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncidentRepo {
    public static boolean save(Incident incident) {
        String query = "INSERT INTO Incident VALUES(?,?,?,?,?,?)";

        System.out.println("IncidentRepo.save()");

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(query)) {
            System.out.println("IncidentRepo.save() - try");

            pstm.setObject(1, incident.getIncidentId());
            pstm.setObject(2, incident.getSectionId());
            pstm.setObject(3, incident.getIncidentType());
            pstm.setObject(4, incident.getIncidentDate());
            pstm.setObject(5, incident.getIncidentTime());
            pstm.setObject(6, incident.getDescription());

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            // Handle the SQLException here, such as logging the error or displaying a message to the user
            e.printStackTrace(); // This prints the exception trace to the console
            return false; // Or handle the error according to your application's requirements
        }
    }

    public static boolean delete(String incidentId) throws SQLException {
        String query = "DELETE FROM Incident WHERE incidentId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, incidentId);

        return pstm.executeUpdate() > 0;
    }
    public static boolean update(Incident incident) throws SQLException {
        String query = "UPDATE Incident SET sectionId = ?, incidentDate = ?, incidentTime = ?, description = ?, incidentType = ? WHERE incidentId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, incident.getSectionId());
        pstm.setObject(2, incident.getIncidentDate());
        pstm.setObject(3, incident.getIncidentTime());
        pstm.setObject(4, incident.getDescription());
        pstm.setObject(5, incident.getIncidentType());
        pstm.setObject(6, incident.getIncidentId());

        return pstm.executeUpdate() > 0;
    }

    public static Incident search(String incidentId) throws SQLException {
        String query = "SELECT * FROM Incident WHERE incidentId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, incidentId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String sectionId = resultSet.getString(2);
            LocalDate incidentDate = resultSet.getDate(3).toLocalDate();
            String incidentTime = resultSet.getString(4);
            String description = resultSet.getString(5);
            String incidentType = resultSet.getString(6);

            Incident incident = new Incident(id, sectionId, incidentType, incidentDate, incidentTime, description);

            return incident;
        }
        return null;
    }

    public static List<Incident> getIncidents(String InInmateId) {
        List<Incident> incidentsRecord = new ArrayList<>();

        return incidentsRecord;
    }
}
