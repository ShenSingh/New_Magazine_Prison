package lk.ijse.gdse69.javafx.Model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Incident {
    private String incidentId;
    private String sectionId;
    private String incidentType;
    private LocalDate incidentDate;
    private String incidentTime;
    private String description;

    @Override
    public String toString() {
        return "Incident{" +
                "incidentId='" + incidentId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", incidentType='" + incidentType + '\'' +
                ", incidentDate='" + incidentDate + '\'' +
                ", incidentTime='" + incidentTime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
