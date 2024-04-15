package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Incident;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncidentRepo {
    public static boolean save(Incident incident) throws SQLException {

        String query = "INSERT INTO Incident VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, incident.getIncidentId());
        pstm.setObject(2, incident.getSectionId());
        pstm.setObject(3, incident.getIncidentDate());
        pstm.setObject(4, incident.getIncidentTime());
        pstm.setObject(5, incident.getDescription());
        pstm.setObject(6, incident.getIncidentType());

        return pstm.executeUpdate() > 0;
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
            String incidentDate = resultSet.getString(3);
            String incidentTime = resultSet.getString(4);
            String description = resultSet.getString(5);
            String incidentType = resultSet.getString(6);

            Incident incident = new Incident(id, sectionId, incidentType, incidentDate, incidentTime, description);

            return incident;
        }
        return null;
    }

}
