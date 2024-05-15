package lk.ijse.gdse69.javafx.Model;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {
    private String uId;
    private String uName;
    private String uEmail;
    private String uPassword;
    private String addressLine1;
    private String addressLine2;
    private String phone;
    private String gender;
    private String dob;
    private byte[] imageData;

}
