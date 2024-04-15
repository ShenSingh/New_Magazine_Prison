package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Expences;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ExpencesRepo {
    public static boolean save(Expences expences) throws SQLException {

        String query = "INSERT INTO Expences VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, expences.getExpenceId());
        pstm.setObject(2, expences.getSectionId());
        pstm.setObject(3, expences.getMonth());
        pstm.setObject(4, expences.getType());
        pstm.setObject(5, expences.getCost());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String expenceId) throws SQLException {
        String query = "DELETE FROM Expences WHERE expenceId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, expenceId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Expences expences) throws SQLException {
        String query = "UPDATE Expences SET sectionId = ?, month = ?, type = ?, cost = ? WHERE expenceId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, expences.getSectionId());
        pstm.setObject(2, expences.getMonth());
        pstm.setObject(3, expences.getType());
        pstm.setObject(4, expences.getCost());
        pstm.setObject(5, expences.getExpenceId());

        return pstm.executeUpdate() > 0;
    }

    public static Expences search(String expenceId) throws SQLException {
        String query = "SELECT * FROM Expences WHERE expenceId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, expenceId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String sectionId = resultSet.getString(2);
            String month = resultSet.getString(3);
            String type = resultSet.getString(4);
            double cost = resultSet.getDouble(5);

            Expences expences = new Expences(id, sectionId, month, type, cost);

            return expences;
        }
        return null;
    }
}
