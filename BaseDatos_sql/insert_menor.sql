CREATE OR REPLACE PROCEDURE insert_menor(
idSolicitante IN menores.idSolicitante%TYPE,
dniMenor IN menores.dniMenor%TYPE,
nombreMenor IN menores.nombreMenor%TYPE,
apel1Menor IN menores.apel1Menor%TYPE,
apel2Menor IN menores.apel2Menor%TYPE,
sexoMenor IN menores.sexoMenor%TYPE,
fechaNacMenor IN menores.fechaNacMenor%TYPE,
discapacidadMenor IN menores.discapacidadMenor%TYPE,
idInscripcion IN inscripciones.idInscripcion%TYPE,
idCentro IN centros.idCentro%TYPE,
idModelo IN modelos.idModelo%TYPE,
pcontrol OUT varchar2
)AS

BEGIN

INSERT INTO MENORES VALUES (idSolicitante_seq.NEXTVAL,dniMenor,nombreMenor,apel1Menor,apel2Menor,sexoMenor,fechaNacMenor,discapacidadMenor,idInscripcion,idCentro,idModelo );
COMMIT;

EXCEPTION
	WHEN DUP_VAL_ON_INDEX THEN
			pcontrol:='Imposible crear solicitud.Se ha intentado duplicar la clave primaria';
			rollback;
	WHEN OTHERS THEN
			pcontrol:='Han Ocurrido Errores';
			rollback;
END insert_menor;