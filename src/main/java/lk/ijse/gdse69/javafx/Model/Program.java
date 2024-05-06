package lk.ijse.gdse69.javafx.Model;

import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Program {
    private String programId;
    private String programName;
    private String sectionId;
    private LocalDate programDate;
    private LocalTime programTime;
    private String description;


    @Override
    public String toString() {
        return "Program{" +
                "programId='" + programId + '\'' +
                ", programName='" + programName + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", programDate='" + programDate + '\'' +
                ", programTime='" + programTime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
