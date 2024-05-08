package lk.ijse.gdse69.javafx.Model;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class SetIncidentRecord {

    private Incident incident;
    List<String> inmateIds;
}
