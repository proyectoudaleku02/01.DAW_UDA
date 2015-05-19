/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: Procedimiento para insertar Tutores en la base de datos

*/
CREATE OR REPLACE PROCEDURE alta_tutor (
dniTutor IN tutores.dniTutor%TYPE,
nombreTutor IN tutores.nombreTutor%TYPE,
apel1 IN tutores.ape1Tutor%TYPE,
apel2 IN tutores.ape2Tutor%TYPE,
idInscripcion IN inscripciones.idInscripcion%TYPE
--pcontrol OUT varchar2
)
AS

BEGIN

INSERT INTO TUTORES VALUES (idSolicitante_seq.NEXTVAL,dniTutor,nombreTutor,apel1,apel2,idInscripcion);
COMMIT;
EXCEPTION
	WHEN DUP_VAL_ON_INDEX THEN
	 --pcontrol:='Imposible crear solicitud.Se ha intentado duplicar la clave primaria';
	 rollback;
	WHEN OTHERS THEN
	--pcontrol:='Han Ocurrido Errores';
	rollback;
END alta_tutor;