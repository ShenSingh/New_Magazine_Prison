package lk.ijse.gdse69.javafx.Model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class Section {
    private String sectionId;
    private String sectionName;
    private String location;
    private Integer capacity;
    private String securityLevel;
    private String status;


    @Override
    public String toString() {
        return "Section{" +
                "sectionId='" + sectionId + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", location='" + location + '\'' +
                ", capacity='" + capacity + '\'' +
                ", securityLevel='" + securityLevel + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
