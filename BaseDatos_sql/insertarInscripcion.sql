/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: procedimiento para crear y probar un procedimiento que cree inscripciones

*/

CREATE OR REPLACE PROCEDURE insert_inscripcion(
idSolicitud IN solicitudes.idSolicitud%TYPE,
pnum IN NUMBER, --NUMERO DE INSCRIPCIONES POR NUMERO DE SOLICITUD (MÁXIMO SERÁ TRES)
idDireccion IN direcciones.idDireccion%TYPE,
idInscripcion OUT inscripciones.idInscripcion%TYPE,
pcontrol OUT varchar2
)
AS
	inscrip_noValid EXCEPTION;
	BEGIN 
		IF (pnum>3) THEN
			RAISE inscrip_noValid;
		ELSE 
			idInscripcion:=idSolicitud||'/'||pnum;
			INSERT INTO INSCRIPCIONES VALUES(idInscripcion,idSolicitud,idDireccion);
			COMMIT;

		END IF; 

	EXCEPTION
	WHEN inscrip_noValid THEN
	pcontrol:='Este número de solicitud ya no puede acarcar más inscripciones';
	WHEN DUP_VAL_ON_INDEX THEN
	pcontrol:='Imposible crear solicitud.Se ha intentado duplicar la clave primaria';
	 rollback;
	WHEN OTHERS THEN
	pcontrol:='Han Ocurrido Errores';
	rollback;

END insert_inscripcion;