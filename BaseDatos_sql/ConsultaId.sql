/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: Paquete  con funciones para hacer la búsqueda de los id
	1.- find_menor: Funcion que busca la menor que se desea inscribir en las colonias. Si lo encuentra en base de datos, no permite la solicitud
	2.- find_solicitud: Devuelve el id de la última solicitud realizada 

*/

------------------------------------------CABECERA--------------------------------------
CREATE OR REPLACE PACKAGE busqueda_id
IS

--búsqueda menor
	FUNCTION find_menor (
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

	return  menores.idSolicitante%TYPE;
	
--búsqueda solicitud
	FUNCTION find_solicitud
	return solicitudes.idSolicitud%TYPE;
	
---búsqueda de direccion
	FUNCTION find_direccion
	RETURN direcciones.idDireccion%TYPE;

------------búsqueda último idInscripcion--
FUNCTION find_inscripcion
	RETURN inscripciones.idInscripcion%TYPE;

END busqueda_id;





---------------------------------------------CUERPO--------------------------------------------
CREATE OR REPLACE PACKAGE BODY busqueda_id 
IS
-------------------------
	FUNCTION find_menor (
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

	Return  menores.idSolicitante%TYPE
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


---------------------------------

	FUNCTION find_solicitud
	RETURN solicitudes.idSolicitud%TYPE
	IS
	idSolicitud solicitudes.idSolicitud%TYPE;
	
	BEGIN
		select max(idSolicitud) 
		into idSolicitud
		from solicitudes;
	EXCEPTION
		WHEN NO_DATA_FOUND THEN
			idSolicitud:=0;
		WHEN OTHERS THEN
			idSolicitud:=0;
	RETURN idSolicitud;

	END find_solicitud;
-----------------------------------

	FUNCTION find_direccion
	RETURN direcciones.idDireccion%TYPE
	IS
	idDireccion direcciones.idDireccion%TYPE;
	BEGIN
		select max(idDireccion) 
		into idDireccion
		from direcciones;
	EXCEPTION
		WHEN NO_DATA_FOUND THEN
			idDireccion:=0;
		WHEN OTHERS THEN
			idDireccion:=0;
	RETURN iddireccion;

	END find_direccion;
	

--------------------------------------------------------
FUNCTION find_inscripcion
	RETURN inscripciones.idInscripcion%TYPE
	IS
	idInscripcion inscripciones.idInscripcion%TYPE;
	BEGIN
		select max(idInscripcion) 
		into idInscripcion
		from inscripciones;
	EXCEPTION
		WHEN NO_DATA_FOUND THEN
			idInscripcion:=0;
		WHEN OTHERS THEN
			idInscripcion:=0;
	RETURN idInscripcion;

	END find_inscripcion;
	


END busqueda_id;