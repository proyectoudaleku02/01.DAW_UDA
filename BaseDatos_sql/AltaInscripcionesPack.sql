/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: Paquete  donde se engloban todos los procedimientos relacionados con las insercciones de datos en la Base de Datos.
	1.- insertar_direccion: pasándole los parámetros introducidos por el usuario insertamos la dirección en base de datos tabla direcciones, 
							asignándole un id  auto incremental (en adelante a.i.).
	2.- insertar_solicitud: le pásamos el la situación o estado de la solicitud (en primera instancia creará una solicitud nueva adjudicándole un id a.i.
							insertar solicitud en tabla solicitudes.
	3.- insert_inscripcion: le pasamos el idSolicitud correspondiente, idDireccion correspondiente y el número de inscripción (1,2 o 3) dentro de ese número de solicitud. 
							Creará una nueva inscripción asignandole un id (varchar2 resultado de una concatenación de valores) relacionandola también con la dirección del solicitante.
	4.-alta_tutor: Da de alta al tutor que realiza la solicitud asignándole un id a.i.
	5.- insertar_tlfn: dependiendo del número de números de telefonos que haya introducido el solicitante, insertará los mismos en base de datos, tabla teléfonos. 
					id será el número en sí y se le relacionará con el solicitante.
	6.- insert_menor: insertamos en la tabla menores, el menor solicitante con todos sus datos asociados. Asignamos al menor un idSolicitante a.i.

*/



CREATE OR REPLACE PACKAGE alta_inscripciones
IS 
	PROCEDURE insertar_direccion(
	pnumDir IN direcciones.numDir%TYPE,
	pletra IN direcciones.letra%TYPE,
	ppiso IN direcciones.piso%TYPE,
	pescalera IN direcciones.escalera%TYPE,
	pmano IN direcciones.mano%TYPE,
	pcp IN direcciones.cp%TYPE,
	pidViaFK IN direcciones.idVia%TYPE);
	--pidDireccion OUT direcciones.idDireccion%TYPE,
	--perror OUT varchar2);
	
	PROCEDURE insertar_solicitud (
		psituacionSolic IN solicitudes.situacionSolic%TYPE
		);

	PROCEDURE insert_inscripcion(
		idSolicitud IN solicitudes.idSolicitud%TYPE,
		pnum IN NUMBER, --NUMERO DE INSCRIPCIONES POR NUMERO DE SOLICITUD (MÁXIMO SERÁ TRES)
		idDireccion IN direcciones.idDireccion%TYPE		
		);

	PROCEDURE alta_tutor (
		dniTutor IN tutores.dniTutor%TYPE,
		nombreTutor IN tutores.nombreTutor%TYPE,
		apel1 IN tutores.ape1Tutor%TYPE,
		apel2 IN tutores.ape2Tutor%TYPE,
		idInscripcion IN inscripciones.idInscripcion%TYPE
		);
	PROCEDURE insertar_tlfn(
		telefono1 IN telefonos.numerotelf%TYPE,
		telefono2 IN telefonos.numerotelf%TYPE,
		telefono3 IN telefonos.numerotelf%TYPE,
		telefono4 IN telefonos.numerotelf%TYPE,
		idSolicitante IN tutores.idSolicitante%TYPE
		);
	PROCEDURE insert_menor(
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
		idModelo IN modelos.idModelo%TYPE
		);
		
END alta_inscripciones;



 -----------DEFINIMOS EL CUERPO DEL PAQUETE-----------------------------

CREATE OR REPLACE PACKAGE BODY alta_inscripciones
IS 

	PROCEDURE insertar_direccion(
	pnumDir IN direcciones.numDir%TYPE,
	pletra IN direcciones.letra%TYPE,
	ppiso IN direcciones.piso%TYPE,
	pescalera IN direcciones.escalera%TYPE,
	pmano IN direcciones.mano%TYPE,
	pcp IN direcciones.cp%TYPE,
	pidViaFK IN direcciones.idVia%TYPE)

	AS 
	
	BEGIN
		
		INSERT INTO DIRECCIONES VALUES (idDireccion_seq.NEXTVAL,pnumDir, pletra, ppiso, pescalera, pmano, pcp, pidViaFK);
		COMMIT;
	
	EXCEPTION
	WHEN DUP_VAL_ON_INDEX THEN
	 
	 rollback;
	WHEN OTHERS THEN
	
	rollback;
	
	END insertar_direccion;
	
	
	
	PROCEDURE insertar_solicitud (
		psituacionSolic IN solicitudes.situacionSolic%TYPE
		)

		AS

		BEGIN

			INSERT INTO SOLICITUDES (idSolicitud,situacionSolic) VALUES (idSolicitud_seq.NEXTVAL,psituacionSolic);
			COMMIT;
	
			EXCEPTION
			WHEN DUP_VAL_ON_INDEX THEN
			
			rollback;
			WHEN OTHERS THEN
			
			rollback;

		END insertar_solicitud;
		
		
		
	PROCEDURE insert_inscripcion(
		idSolicitud IN solicitudes.idSolicitud%TYPE,
		pnum IN NUMBER, --NUMERO DE INSCRIPCIONES POR NUMERO DE SOLICITUD (MÁXIMO SERÁ TRES)
		idDireccion IN direcciones.idDireccion%TYPE
		)
	AS
	idInscripcion inscripciones.idInscripcion%TYPE;
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
	 rollback;
	WHEN DUP_VAL_ON_INDEX THEN
	
	 rollback;
	WHEN OTHERS THEN
	
	rollback;
END insert_inscripcion;


	PROCEDURE alta_tutor (
		dniTutor IN tutores.dniTutor%TYPE,
		nombreTutor IN tutores.nombreTutor%TYPE,
		apel1 IN tutores.ape1Tutor%TYPE,
		apel2 IN tutores.ape2Tutor%TYPE,
		idInscripcion IN inscripciones.idInscripcion%TYPE
			)
		AS

	BEGIN

		INSERT INTO TUTORES VALUES (idSolicitante_seq.NEXTVAL,dniTutor,nombreTutor,apel1,apel2,idInscripcion);
		COMMIT;
	EXCEPTION
		WHEN DUP_VAL_ON_INDEX THEN
		rollback;
		WHEN OTHERS THEN
		rollback;
	END alta_tutor;
	
	PROCEDURE insertar_tlfn(
		telefono1 IN telefonos.numerotelf%TYPE,
		telefono2 IN telefonos.numerotelf%TYPE,
		telefono3 IN telefonos.numerotelf%TYPE,
		telefono4 IN telefonos.numerotelf%TYPE,
		idSolicitante IN tutores.idSolicitante%TYPE
		)
		AS
			error_telefono EXCEPTION;
	BEGIN
		IF (telefono1=null)THEN
			RAISE error_telefono;
		ELSIF (telefono2=null) THEN
			INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
		ELSIF (telefono3=null) THEN
			INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono2,idSolicitante);
		ELSIF (telefono4=null) THEN
			INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono2,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono3,idSolicitante);
		ELSE
			INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono2,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono3,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono4,idSolicitante);
		END IF;
		COMMIT;
	EXCEPTION
		WHEN error_telefono THEN
			rollback;
		WHEN DUP_VAL_ON_INDEX THEN
			rollback;
		WHEN OTHERS THEN
			rollback;

	END insertar_tlfn;
	
	 PROCEDURE insert_menor(
		dniMenor IN menores.dniMenor%TYPE,
		nombreMenor IN menores.nombreMenor%TYPE,
		apel1Menor IN menores.apel1Menor%TYPE,
		apel2Menor IN menores.apel2Menor%TYPE,
		sexoMenor IN menores.sexoMenor%TYPE,
		fechaNacMenor IN menores.fechaNacMenor%TYPE,
		discapacidadMenor IN menores.discapacidadMenor%TYPE,
		idInscripcion IN inscripciones.idInscripcion%TYPE,
		idCentro IN centros.idCentro%TYPE,
		idModelo IN modelos.idModelo%TYPE
		)AS

		BEGIN

			INSERT INTO MENORES VALUES (idSolicitante_seq.NEXTVAL,dniMenor,nombreMenor,apel1Menor,apel2Menor,sexoMenor,fechaNacMenor,discapacidadMenor,idInscripcion,idCentro,idModelo );
			COMMIT;

		EXCEPTION
			WHEN DUP_VAL_ON_INDEX THEN
					rollback;
			WHEN OTHERS THEN
					rollback;
		END insert_menor;

		
END alta_inscripciones;

