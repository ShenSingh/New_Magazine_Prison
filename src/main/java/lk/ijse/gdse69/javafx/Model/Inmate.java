package lk.ijse.gdse69.javafx.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inmate {

    private String inmateId;
    private String inmateFirstName;
    private String inmateLastName;
    private LocalDate inmateDOB;
    private String inmateNIC;
    private String gender;
    private String inmateAddress;
    private String status;


    @Override
    public String toString() {
        return "Inmate{" +
                "inmateId='" + inmateId + '\'' +
                ", inmateFirstName='" + inmateFirstName + '\'' +
                ", inmateLastName='" + inmateLastName + '\'' +
                ", inmateDOB='" + inmateDOB + '\'' +
                ", inmateNIC='" + inmateNIC + '\'' +
                ", gender='" + gender + '\'' +
                ", inmateAddress='" + inmateAddress + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
