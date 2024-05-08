package lk.ijse.gdse69.javafx.Model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class IncidentRelatedInmate {

    private String incidentID;
    private String inmateID;


    @Override
    public String toString() {
        return "IncidentRelatedInmate{" +
                ", incidentID='" + incidentID + '\'' +
                ", inmateID='" + inmateID + '\'' +
                '}';
    }
}
