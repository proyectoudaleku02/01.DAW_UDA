/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: Procedimiento para insertar Tutores en la base de datos

*/
CREATE OR REPLACE PROCEDURE alta_tutor (
dniTutor IN tutores.dniTutor%TYPE,
nombreTutor IN tutores.nombreTutor%TYPE,
apel1 IN tutores.apel1Tutor%TYPE,
apel2 IN tutores.apel2Tutor%TYPE,
idIncripcion IN inscripciones.idIndcricion%TYPE
)
AS

BEGIN
INSERT INTO TUTORES VALUES (dniTutor,nombreTutor,apel1Tutor,apel2Tutor,idIncripcion);
COMMIT;

END alta_tutor;