	/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: Procedimiento para insertar nueva solicitud en la base de datos
	el procedimiento recibe como parámetros todos los datos relativos a la Solicitud, para la fase  de inscripcion:
				-situación
				
				y devuelve por un lado el idSolicitud (lo necesitamos para crear el idIncripcion y relacionarlo con el menor y el tutor)

*/


CREATE OR REPLACE PROCEDURE insertar_solicitud (
psituacionSolic IN solicitudes.situacionSolic%TYPE,
pidSolicitud out solicitudes.idSolicitud%TYPE,
perror OUT varchar2(45),
)
AS

BEGIN

	pidSolicitud:=idSolicitud_seq.NEXTVAL;
	INSERT INTO SOLICITUDES (idSolicitud,situacionSolic) VALUES (pidSolicitud,psituacionSolic);
	COMMIT;
	
	EXCEPTION
	WHEN DUP_VAL_ON_INDEX THEN
	 perror:='Imposible crear solicitud.Se ha intentado duplicar la clave primaria';
	 rollback;
	WHEN OTHERS THEN
	perror:='Han Ocurrido Errores';
	rollback;

END;

