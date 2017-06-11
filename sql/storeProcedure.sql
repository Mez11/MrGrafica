DELIMITER $$
CREATE PROCEDURE Grafiquita()
BEGIN
    SELECT COUNT(*) AS alumnosTotales, Esc.nombre AS nombreEscuela FROM Alumno AS Al
    INNER JOIN Escuela AS Esc
    ON Esc.id = Al.idEscuela
    GROUP BY Esc.id
    ;
END $$
DELIMITER ;
