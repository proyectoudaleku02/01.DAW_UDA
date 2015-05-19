/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: se crea un paquete que se ejecutará cuando todos los datos introducidos en la vista de inscripción estén rellenados y validados y se quiera formalizar la inscripción.
	1. Se dará de alta al tutor y al menor mediante procedimientos independientes

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
		idInscripcion IN inscripciones.idInscripcion%TYPE,
		pcontrol OUT varchar2
		);
	PROCEDURE insertar_tlfn(
		telefono1 IN telefonos.numerotelf%TYPE,
		telefono2 IN telefonos.numerotelf%TYPE,
		telefono3 IN telefonos.numerotelf%TYPE,
		telefono4 IN telefonos.numerotelf%TYPE,
		idSolicitante IN tutores.idSolicitante%TYPE,
		pcontrol OUT varchar2
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
		idModelo IN modelos.idModelo%TYPE,
		pcontrol OUT varchar2
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
		idInscripcion IN inscripciones.idInscripcion%TYPE,
		pcontrol OUT varchar2
			)
		AS

	BEGIN

		INSERT INTO TUTORES VALUES (idSolicitante_seq.NEXTVAL,dniTutor,nombreTutor,apel1,apel2,idInscripcion);
		COMMIT;
	EXCEPTION
		WHEN DUP_VAL_ON_INDEX THEN
		pcontrol:='Imposible crear solicitud.Se ha intentado duplicar la clave primaria';
		rollback;
		WHEN OTHERS THEN
		pcontrol:='Han Ocurrido Errores';
		rollback;
	END alta_tutor;
	
	PROCEDURE insertar_tlfn(
		telefono1 IN telefonos.numerotelf%TYPE,
		telefono2 IN telefonos.numerotelf%TYPE,
		telefono3 IN telefonos.numerotelf%TYPE,
		telefono4 IN telefonos.numerotelf%TYPE,
		idSolicitante IN tutores.idSolicitante%TYPE,
		pcontrol OUT varchar2
		)
		AS
			error_telefono EXCEPTION;
	BEGIN
		IF (telefono1='         ')THEN
			RAISE error_telefono;
		ELSIF (telefono2='         ') THEN
			pcontrol:='solo un teléfono de contacto para este solicitante';
			INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
		ELSIF (telefono3='         ') THEN
			pcontrol:='Hay dos teléfonos de contacto para este solicitante';
			INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono2,idSolicitante);
		ELSIF (telefono4='         ') THEN
			pcontrol:='Hay tres teléfonos de contacto para este solicitante';
			INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono2,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono3,idSolicitante);
		ELSE
			pcontrol:='Hay tres teléfonos de contacto para este solicitante';
			INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono2,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono3,idSolicitante);
			INSERT INTO TELEFONOS VALUES (telefono4,idSolicitante);
		END IF;
		COMMIT;
	EXCEPTION
		WHEN error_telefono THEN
			pcontrol:='No se han introducido teléfonos de contacto';
		WHEN DUP_VAL_ON_INDEX THEN
			pcontrol:='Imposible crear solicitud.Se ha intentado duplicar la clave primaria';
			rollback;
		WHEN OTHERS THEN
			pcontrol:='Han Ocurrido Errores';
			rollback;

	END insertar_tlfn;
	
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

		
END alta_inscripciones;

