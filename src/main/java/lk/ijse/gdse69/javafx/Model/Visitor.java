package lk.ijse.gdse69.javafx.Model;

public class Visitor {
    private String visitorID;
    private String visitorFirstName;
    private String visitorLastName;
    private String visitorDOB;
    private String visitorNIC;
    private String visitorNumber;
    private String visitorAddress;
    private String relationship;

    public Visitor() {
    }

    public Visitor(String visitorID, String visitorFirstName, String visitorLastName, String visitorDOB,  String visitorNIC, String visitorNumber, String visitorAddress, String relationship) {
        this.visitorID = visitorID;
        this.visitorFirstName = visitorFirstName;
        this.visitorLastName = visitorLastName;
        this.visitorDOB = visitorDOB;
        this.visitorNIC = visitorNIC;
        this.visitorNumber = visitorNumber;
        this.visitorAddress = visitorAddress;
        this.relationship = relationship;
    }

    public String getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(String visitorID) {
        this.visitorID = visitorID;
    }

    public String getVisitorFirstName() {
        return visitorFirstName;
    }

    public void setVisitorFirstName(String visitorFirstName) {
        this.visitorFirstName = visitorFirstName;
    }
    public String getVisitorLastName() {
        return visitorLastName;
    }

    public void setVisitorLastName(String visitorLastName) {
        this.visitorLastName = visitorLastName;
    }

    public String getVisitorDOB() {
        return visitorDOB;
    }

    public void setVisitorDOB(String visitorDOB) {
        this.visitorDOB = visitorDOB;
    }

    public String getVisitorNIC() {
        return visitorNIC;
    }

    public void setVisitorNIC(String visitorNIC) {
        this.visitorNIC = visitorNIC;
    }

    public String getVisitorNumber() {
        return visitorNumber;
    }

    public void setVisitorNumber(String visitorNumber) {
        this.visitorNumber = visitorNumber;
    }
    public String getVisitorAddress() {
        return visitorAddress;
    }

    public void setVisitorAddress(String visitorAddress) {
        this.visitorAddress = visitorAddress;
    }


    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "visitorID='" + visitorID + '\'' +
                ", visitorFirstName='" + visitorFirstName + '\'' +
                ", visitorLastName='" + visitorLastName + '\'' +
                ", visitorDOB='" + visitorDOB + '\'' +
                ", visitorNIC='" + visitorNIC + '\'' +
                ", visitorNumber='" + visitorNumber + '\'' +
                ", visitorAddress='" + visitorAddress + '\'' +
                ", relationship='" + relationship + '\'' +
                '}';
    }
}
