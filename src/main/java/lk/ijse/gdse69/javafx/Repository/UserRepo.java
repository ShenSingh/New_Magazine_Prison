package lk.ijse.gdse69.javafx.Repository;

import lk.ijse.gdse69.javafx.Model.User;
import lk.ijse.gdse69.javafx.Util.CrudUtil;
import lk.ijse.gdse69.javafx.db.DbConnection;
import lk.ijse.gdse69.javafx.jbcrypt.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {

    public static boolean save(User user) throws SQLException{

        String query = "INSERT INTO User VALUES(?,?,?,?)";

        System.out.println("uIddddd  "+user.getuId());


        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, user.getuId());
        pstm.setObject(2, user.getuName());
        pstm.setObject(3, user.getuEmail());
        pstm.setObject(4, user.getuPassword());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String uId) throws SQLException{
        String query = "DELETE FROM User WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, uId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(User user) throws SQLException{
        String query = "UPDATE User SET uName = ?, uPassword = ?, uEmail = ? WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, user.getuName());
        pstm.setObject(2, user.getuPassword());
        pstm.setObject(3, user.getuEmail());
        pstm.setObject(4, user.getuId());

        return pstm.executeUpdate() > 0;
    }

    public static User search(String  uId) throws SQLException{
        String query = "SELECT * FROM User WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, uId);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            String email = resultSet.getString(4);

            User user = new User(id,name, password, email);

            return user;
        }

        return null;
    }

    public boolean checkUser(String uId) throws SQLException {
        String query = "SELECT * FROM User WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, uId);

        ResultSet resultSet = pstm.executeQuery();

        return resultSet.next();
    }

    public static boolean valid(String uId, String password) throws SQLException {
        PasswordHasher passwordHasher = new PasswordHasher();
        String sql="SELECT * FROM User WHERE uId = ?";
        ResultSet resultSet= CrudUtil.execute(sql,uId);
        if(resultSet.next()){

            if(passwordHasher.checkPassword(password,resultSet.getString(4))){
                return true;
            }
            return false;
        }
        return false;
    }

}
