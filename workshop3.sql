CREATE DATABASE workshop3;

USE workshop3;

CREATE TABLE users
(
    id       INT AUTO_INCREMENT
        PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(60)  NOT NULL,
    CONSTRAINT email
        UNIQUE (email)
)