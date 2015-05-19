/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: Procedimiento para buscar en base de datos al menor que solicita inscribirse en Udalekuak.
	si encuentra devolvemos el idSolicitante con el que ya ha hecho la solicitud y sino el idSolicitante valdrá 0

*/
CREATE OR REPLACE FUNCTION find_menor (
		dni IN menores.dniMenor%TYPE,
		nombre IN menores.nombreMenor%TYPE,
		apel1 IN menores.apel1Menor%TYPE,
		apel2 IN menores.apel2Menor%TYPE,
		sexo IN menores.sexoMenor%TYPE,
		fechaNac IN menores.fechaNacMenor%TYPE,
		discapacidad IN menores.discapacidadMenor%TYPE,
		pidCentro IN centros.idCentro%TYPE,
		pidModelo IN modelos.idModelo%TYPE
)

return  menores.idSolicitante%TYPE
IS

idSolicitante menores.idSolicitante%TYPE;

BEGIN

	IF (dni IS NOT NULL) THEN

		select idSolicitante into idSolicitante
		from menores
		where lower(dniMenor)=lower(dni);

	ELSE
		SELECT idSolicitante into idSolicitante
		from menores
		where (lower(nombreMenor)=lower(nombre) and lower(apel1Menor)=lower(apel1) and lower(apel2Menor)=lower(apel2) 
		and sexoMenor=sexo and fechaNacMenor=fechaNac and discapacidadMenor=discapacidad and idCentro=pidCentro and idModelo=pidModelo);

END IF;

EXCEPTION
	WHEN NO_DATA_FOUND THEN
	idSolicitante:=0;
	WHEN OTHERS THEN
	idSolicitante:=0;


RETURN idSolicitante;

END find_menor;


