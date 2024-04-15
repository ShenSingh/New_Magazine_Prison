package lk.ijse.gdse69.javafx.Model;

public class Expences {
    private String expenceId;
    private String sectionId;
    private String month;
    private String type;
    private double cost;

    public Expences() {
    }
    public Expences(String expenceId, String sectionId, String month, String type, double cost) {
        this.expenceId = expenceId;
        this.sectionId = sectionId;
        this.month = month;
        this.type = type;
        this.cost = cost;
    }

    public String getExpenceId() {
        return expenceId;
    }

    public void setExpenceId(String expenceId) {
        this.expenceId = expenceId;
    }
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

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
