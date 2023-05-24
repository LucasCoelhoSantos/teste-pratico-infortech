CREATE SCHEMA `infortech` DEFAULT CHARACTER SET utf8;

CREATE database infortech;

DROP TABLE IF EXISTS carros;

CREATE table carros(
    numeroDeSerie int NOT NULL auto_increment primary key,
    capacidade double,
    portador varchar(40),
    combustivelDisponivel double
);
