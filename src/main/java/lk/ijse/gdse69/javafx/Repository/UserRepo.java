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

        String query = "INSERT INTO User VALUES(?,?,?,?,?,?,?,?,?,?)";

        System.out.println("uIddddd  "+user.getUId());

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, user.getUId());
        pstm.setObject(2, user.getUName());
        pstm.setObject(3, user.getUEmail());
        pstm.setObject(4, user.getUPassword());
        pstm.setObject(5, user.getAddressLine1());
        pstm.setObject(6, user.getAddressLine2());
        pstm.setObject(7, user.getPhone());
        pstm.setObject(8, user.getGender());
        pstm.setObject(9, user.getDob());
        pstm.setObject(10, user.getImageData());

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
        String query = "UPDATE User SET uName = ?, uPassword = ?, uEmail = ?, addressLine1 = ?, addressLine2 = ?, phone = ?, gender = ?, DOB = ?, imageData = ?  WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, user.getUName());
        pstm.setObject(2, user.getUPassword());
        pstm.setObject(3, user.getUEmail());
        pstm.setObject(4, user.getAddressLine1());
        pstm.setObject(5, user.getAddressLine2());
        pstm.setObject(6, user.getPhone());
        pstm.setObject(7, user.getGender());
        pstm.setObject(8, user.getDob());
        pstm.setObject(9, user.getImageData());
        pstm.setObject(10, user.getUId());

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
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            String address1 = resultSet.getString(5);
            String address2 = resultSet.getString(6);
            String phone = resultSet.getString(7);
            String gender = resultSet.getString(8);
            String dob = resultSet.getString(9);
            byte[] imageData = resultSet.getBytes(10);

            User user = new User(id,name,email,password,address1,address2,phone,gender,dob,imageData);

            return user;
        }

        return null;
    }

    public static boolean updateId(String userIdValue, String text) throws SQLException {
        String query = "UPDATE User SET  uId = ?  WHERE uId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setObject(1, text);
        pstm.setObject(2, userIdValue);

        return pstm.executeUpdate() > 0;
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
