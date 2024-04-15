
CREATE TABLE User (
                      uId VARCHAR(20) PRIMARY KEY,
                      uName VARCHAR(50) UNIQUE NOT NULL,
                      uEmail VARCHAR(50) UNIQUE NOT NULL,
                      uPassword VARCHAR(200) NOT NULL
);

CREATE TABLE Section(
                        sectionId VARCHAR(50) PRIMARY KEY,
                        sectionName VARCHAR(100),
                        location VARCHAR(100),
                        capacity VARCHAR(100),
                        securityLevel VARCHAR(50)
);

CREATE TABLE Inmate(
                       inmateId VARCHAR(50) PRIMARY KEY,
                       inmateFirstName VARCHAR(100) UNIQUE,
                       inmateLastName VARCHAR(100),
                       inmateDOB DATE,
                       inmateNIC VARCHAR(20) UNIQUE,
                       gender VARCHAR(20),
                       inmateAddress VARCHAR(100),
                       status VARCHAR(100)
);

ALTER TABLE InmateRecord
    CHANGE COLUMN entrydate entryDate DATE;


CREATE  TABLE InmateRecord(
                              inmateRecordId VARCHAR(50) PRIMARY KEY,
                              inmateId  VARCHAR(50),
                              sectionId VARCHAR(50),
                              entryDate DATE,
                              releaseDate DATE,
                              crime VARCHAR(100),
                              caseStatus VARCHAR(50),
                              FOREIGN KEY (sectionId) REFERENCES Section(sectionId) ON UPDATE CASCADE ON DELETE CASCADE,
                              FOREIGN KEY (inmateId) REFERENCES Inmate(inmateId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Officer(
                        officerId VARCHAR(50) PRIMARY KEY,
                        officerFirstName VARCHAR(100) UNIQUE,
                        officerLastName VARCHAR(100),
                        officerDOB DATE,
                        officerNIC VARCHAR(20),
                        gender VARCHAR(20),
                        officerAddress varchar(100),
                        officerEmail VARCHAR(100),
                        officerNumber INT(10),
                        position VARCHAR(50),
                        sectionId VARCHAR(50),
                        salary Decimal(10,2),
                        FOREIGN KEY (sectionId) REFERENCES Section(sectionId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Visitor(
                        visitorId VARCHAR(50) PRIMARY KEY,
                        visitorfristName VARCHAR(100) UNIQUE,
                        visitorLastName VARCHAR(100),
                        visitorDOB DATE,
                        visitorNIC VARCHAR(20),
                        visitorNumber INT(10),
                        visitorAddress VARCHAR(100),
                        relationship VARCHAR(50)
);

CREATE TABLE VisitorRecord(
                              visitorRecordId VARCHAR(50) PRIMARY KEY,
                              visitorId VARCHAR(50),
                              inmateId varchar(50),
                              visitDate DATE,
                              visitTime TIME,
                              FOREIGN KEY (visitorId) REFERENCES Visitor(visitorId) ON UPDATE CASCADE ON DELETE CASCADE,
                              FOREIGN KEY (inmateId) REFERENCES Inmate(inmateId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Incident(
                         incidentId VARCHAR(50) PRIMARY KEY,
                         sectionId VARCHAR(50),
                         incidentType VARCHAR(100),
                         incidentDate DATE,
                         incidentTime TIME,
                         description VARCHAR(100),
                         FOREIGN KEY (sectionId) REFERENCES Section(sectionId) ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE IncidentRelatedInmate(
                                      No INT auto_increment PRIMARY KEY ,
                                      incidentId VARCHAR(50),
                                      inmateId VARCHAR(50),
                                      FOREIGN KEY (incidentId) REFERENCES Incident(incidentId) ON UPDATE CASCADE ON DELETE CASCADE,
                                      FOREIGN KEY (inmateId) REFERENCES Inmate(inmateId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Program(
                        programId VARCHAR(50) PRIMARY KEY,
                        programName VARCHAR(100),
                        sectionId VARCHAR(50),
                        programDate DATE,
                        programTime TIME,
                        description VARCHAR(100),
                        FOREIGN KEY (sectionId) REFERENCES Section(sectionId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Expences(
    expencesId VARCHAR(50) PRIMARY KEY,
    sectionId VARCHAR(50),
                         month VARCHAR(50),
                         type VARCHAR(50),
                         cost DECIMAL(10,2),
                         FOREIGN KEY (sectionId) REFERENCES Section(sectionId) ON UPDATE CASCADE ON DELETE CASCADE
);

show tables;



