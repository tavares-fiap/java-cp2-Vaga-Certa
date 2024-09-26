DROP DATABASE PARKINGLOT;
CREATE DATABASE PARKINGLOT;
USE PARKINGLOT;

CREATE TABLE VEHICLE
(
    license_plate VARCHAR(7),
    brand VARCHAR(20),
    model VARCHAR(20),
    color VARCHAR(10),
    entry_time TIME NOT NULL,
    PRIMARY KEY (license_plate)
);

INSERT INTO VEHICLE (license_plate, brand, model, color, entry_time)
VALUES ('ABC1234', 'Toyota', 'Corolla', 'Prata', '08:00:00');

INSERT INTO VEHICLE (license_plate, brand, model, color, entry_time)
VALUES ('DEF5678', 'Honda', 'Civic', 'Preto', '09:00:00');

SELECT * FROM VEHICLE;