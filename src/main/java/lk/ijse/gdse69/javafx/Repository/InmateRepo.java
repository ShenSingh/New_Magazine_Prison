package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Inmate;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InmateRepo {

    public static boolean save(Inmate inmate) throws SQLException {

        String query = "INSERT INTO Inmate VALUES(?,?,?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, inmate.getInmateId());
        pstm.setObject(2, inmate.getInmateFirstName());
        pstm.setObject(3, inmate.getInmateLastName());
        pstm.setObject(4, inmate.getInmateDOB());
        pstm.setObject(5, inmate.getInmateNIC());
        pstm.setObject(6, inmate.getGender());
        pstm.setObject(7, inmate.getInmateAddress());
        pstm.setObject(8, inmate.getStatus());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String inmateId) throws SQLException {
        String query = "DELETE FROM Inmate WHERE inmateId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, inmateId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Inmate inmate) throws SQLException {
        String query = "UPDATE Inmate SET inmateFirstName = ?," +
                " inmateLastName = ?, inmateDOB = ?, inmateNIC = ?, gender = ?, inmateAddress = ?, status = ? WHERE inmateId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, inmate.getInmateFirstName());
        pstm.setObject(2, inmate.getInmateLastName());
        pstm.setObject(3, inmate.getInmateDOB());
        pstm.setObject(4, inmate.getInmateNIC());
        pstm.setObject(5, inmate.getGender());
        pstm.setObject(6, inmate.getInmateAddress());
        pstm.setObject(7, inmate.getStatus());
        pstm.setObject(8, inmate.getInmateId());

        return pstm.executeUpdate() > 0;
    }

    public static Inmate search(String inmateId) throws SQLException {
        String query = "SELECT * FROM Inmate WHERE inmateId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, inmateId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String dob = resultSet.getString(4);
            String nic = resultSet.getString(5);
            String gender = resultSet.getString(6);
            String address = resultSet.getString(7);
            String status = resultSet.getString(8);

            Inmate inmate = new Inmate(id,firstName,lastName,dob,nic,gender,address,status);
        return inmate;
        }
        return null;
    }
}
