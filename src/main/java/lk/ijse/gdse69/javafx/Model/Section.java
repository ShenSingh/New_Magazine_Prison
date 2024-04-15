package lk.ijse.gdse69.javafx.Model;

public class Section {
    private String sectionId;
    private String sectionName;
    private String location;
    private String capacity;
    private String securityLevel;


    public Section() {
    }

    public Section(String sectionId, String sectionName, String location, String capacity, String securityLevel) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.location = location;
        this.capacity = capacity;
        this.securityLevel = securityLevel;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionId='" + sectionId + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", location='" + location + '\'' +
                ", capacity='" + capacity + '\'' +
                ", securityLevel='" + securityLevel + '\'' +
                '}';
    }
}
