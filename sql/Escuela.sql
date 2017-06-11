DROP DATABASE IF EXISTS Escuela;

create database Escuela;

use Escuela;

create table Escuela(
    id int not null auto_increment,
    nombre varchar(30), 
    primary key (id)  
) ;

create table Alumno(
    id int not null  auto_increment,
    nombre varchar (35),
    apellido varchar (45),
    foto MEDIUMBLOB,
    idEscuela int not null,
    primary key (id),
    foreign key(idEscuela) references Escuela(id) 
);



