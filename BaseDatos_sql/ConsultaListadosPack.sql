/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: Paquete  donde se engloban todos los procedimientos relacionados con la consulta de listados
	1.- listado_previo_total: 
	2.- listado_previo_personal: 

*/
CREATE OR REPLACE PACKAGE consulta_listados
IS
TYPE tcursor IS REF CURSOR;
	PROCEDURE listado_previo_total (
	
	);
	
	PROCEDURE consulta_solicitud_personal (
	pdniTutor IN tutores.dniTutor%TYPE,
	pfechNac IN menores.fechNacMenor%TYPE
	);

END consulta_listados;

---------------------CUERPO DEL PAQUETE-------------------------------------------------
CREATE OR REPLACE PACKAGE BODY consulta_listados
IS

	 PROCEDURE listado_previo_total(
	pmensaje OUT varchar2
	)
	AS
		
	CURSOR clistado IS 
	select idInscripcion, apel1Menor, apel2menor, nombreMenor
	from menores
	ORDER BY apel1Menor;
		
	solicitante clistado%rowtype;
	
	BEGIN
	
		OPEN clistado;

			LOOP

			FETCH clistado INTO solicitante;
			EXIT WHEN clistado%NOTFOUND; /*SALE CUANDO EL FETCH YA NO CAPTA NADA*/
			pmensaje:=pmensaje||'El apellido es '|| empleado.apellido ||' y su fecha de alta '||empleado.fecha_alt||'  ';

			END LOOP;

		CLOSE clistado;
	
	END listado_previo_total;
-------------------------------------------------------------------------------------------------------------------
PROCEDURE consulta_solicitud_personal (
	pdniTutor IN tutores.dniTutor%TYPE,
	pfechNac IN menores.fechNacMenor%TYPE
	)AS


END consulta_listados;