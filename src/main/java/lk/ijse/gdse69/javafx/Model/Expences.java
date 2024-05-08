package lk.ijse.gdse69.javafx.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data


public class Expences {
    private String expenceId;
    private String sectionId;
    private String month;
    private String type;
    private double cost;


    @Override
    public String toString() {
        return "Expences{" +
                "expenceId='" + expenceId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", month='" + month + '\'' +
                ", type='" + type + '\'' +
                ", cost=" + cost +
                '}';
    }
}
