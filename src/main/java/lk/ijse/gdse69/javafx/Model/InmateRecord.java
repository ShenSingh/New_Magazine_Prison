package lk.ijse.gdse69.javafx.Model;

import lombok.*;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class InmateRecord {
    private String inmateId;
    private String sectionId;
    private Date entryDate;
    private Date releaseDate;
    private String crime;
    private String caseStatus;

    @Override
    public String toString() {
        return "InmateRecord{" +
                ", inmateId='" + inmateId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", crime='" + crime + '\'' +
                ", caseStatus='" + caseStatus + '\'' +
                '}';
    }
}
