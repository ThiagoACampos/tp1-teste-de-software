CREATE TABLE User (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
	password VARCHAR(100) NOT NULL
);

CREATE TABLE Patient (
    id INT PRIMARY KEY,
    birthdate DATE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    FOREIGN KEY (id) REFERENCES User(id)
);

CREATE TABLE Doctor (
	id INT PRIMARY KEY,
	expertise VARCHAR(100) NOT NULL,
    crm VARCHAR(50) NOT NULL,
    FOREIGN KEY (id) REFERENCES User(id)
);

CREATE TABLE Nurse (
	id INT PRIMARY KEY,
	expertise VARCHAR(100) NOT NULL,
	coren VARCHAR(50) NOT NULL,
    FOREIGN KEY (id) REFERENCES User(id)
);

CREATE TABLE Appointment (
	id INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT NOT NULL,
    idPatient INT NOT NULL,
    type VARCHAR(10) NOT NULL,
    FOREIGN KEY (idUser) REFERENCES User(id),
    FOREIGN KEY (idPatient) REFERENCES Patient(id)
);

/*
CREATE TABLE DoctorAppointment (
	id INT PRIMARY KEY,
    idDoctor INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Appointment(id),
    FOREIGN KEY (idDoctor) REFERENCES Doctor(id)
);

CREATE TABLE NurseAppointment (
	id INT PRIMARY KEY,
    idNurse INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Appointment(id),
    FOREIGN KEY (idNurse) REFERENCES Nurse(id)
);
*/

CREATE TABLE Equipment (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    consumable TINYINT(1) NOT NULL,
    max INT
);

CREATE TABLE Storage (
	id INT PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE StorageEquipment (
	idStorage INT NOT NULL,
    idEquipment INT NOT NULL,
    PRIMARY KEY (idStorage, idEquipment),
    FOREIGN KEY (idStorage) REFERENCES Storage(id),
    FOREIGN KEY (idEquipment) REFERENCES Equipment(id)
);