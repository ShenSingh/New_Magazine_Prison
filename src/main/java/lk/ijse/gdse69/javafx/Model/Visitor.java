package lk.ijse.gdse69.javafx.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Visitor {
    private String visitorID;
    private String visitorFirstName;
    private String visitorLastName;
    private Date visitorDOB;
    private String visitorNIC;
    private Integer visitorNumber;
    private String visitorAddress;
    private String visitorType;
    private String gender;


}
