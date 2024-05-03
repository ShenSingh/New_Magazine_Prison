package lk.ijse.gdse69.javafx.Model;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class VisitorRecord {
    private String visitorRecordId;
    private String visitorId;
    private String inmateId;
    private Date visitDate;
    private Time visitTime;

}
