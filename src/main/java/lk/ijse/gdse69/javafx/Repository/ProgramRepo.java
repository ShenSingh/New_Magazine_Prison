package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.Program;
import lk.ijse.gdse69.javafx.db.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ProgramRepo {

    public static boolean save(Program program) throws SQLException {
        String query = "INSERT INTO Program VALUES(?,?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, program.getProgramId());
        pstm.setObject(2, program.getProgramName());
        pstm.setObject(3, program.getSectionId());
        pstm.setObject(4, program.getProgramDate());
        pstm.setObject(5, program.getProgramTime());
        pstm.setObject(6, program.getDescription());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String programId) throws SQLException {
        String query = "DELETE FROM Program WHERE programId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, programId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Program program) throws SQLException {
        String query = "UPDATE Program SET programName = ?, sectionId = ?, programDate = ?, programTime = ?, description = ? WHERE programId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, program.getProgramName());
        pstm.setObject(2, program.getSectionId());
        pstm.setObject(3, program.getProgramDate());
        pstm.setObject(4, program.getProgramTime());
        pstm.setObject(5, program.getDescription());
        pstm.setObject(6, program.getProgramId());

        return pstm.executeUpdate() > 0;
    }

    public static Program search(String programId) throws SQLException {
        String query = "SELECT * FROM Program WHERE programId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, programId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String sectionId = resultSet.getString(3);
            String date = resultSet.getString(4);
            String time = resultSet.getString(5);
            String description = resultSet.getString(6);

            Program program = new Program(id, name, sectionId, date, time, description);

            return program;
        }
        return null;
    }
}
