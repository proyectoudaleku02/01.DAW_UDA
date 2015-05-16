



CREATE OR REPLACE PROCEDURE insertar_tlfn(
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
	IF (telefono1=0)THEN
		RAISE error_telefono;
	ELSIF (telefono2=0) THEN
		pcontrol:='solo un teléfono de contacto para este solicitante';
		INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
	ELSIF (telefono3=0) THEN
		pcontrol:='Hay dos teléfonos de contacto para este solicitante';
		INSERT INTO TELEFONOS VALUES (telefono1,idSolicitante);
		INSERT INTO TELEFONOS VALUES (telefono2,idSolicitante);
	ELSIF (telefono4=0) THEN
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