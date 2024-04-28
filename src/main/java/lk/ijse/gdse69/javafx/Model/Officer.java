package lk.ijse.gdse69.javafx.Model;

import lombok.*;


import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Officer {
    private String officerId;
    private String officerFirstName;
    private String officerLastName;
    private Date   officerDOB;
    private String officerNIC;
    private String gender;
    private String officerAddress;
    private String officerEmail;
    private String officerNumber;
    private String position;
    private String sectionId;
    private double salary;


    @Override
    public String toString() {
        return "Officer{" +
                "officerId='" + officerId + '\'' +
                ", officerFirstName='" + officerFirstName + '\'' +
                ", officerLastName='" + officerLastName + '\'' +
                ", officerDOB='" + officerDOB + '\'' +
                ", officerNIC='" + officerNIC + '\'' +
                ", gender='" + gender + '\'' +
                ", officerAddress='" + officerAddress + '\'' +
                ", officerEmail='" + officerEmail + '\'' +
                ", officerNumber='" + officerNumber + '\'' +
                ", position='" + position + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
