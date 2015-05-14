	/*
	Autor: Grupo Sergio, Álvaro y Noemi

	Descripcion: Procedimiento para insertar Dirección en la base de datos
	el procedimiento recibe como parámetros todos los datos relativos a la dirección:
				-Numero
				-Letra
				-Piso
				-Escalera
				-Mano
				-Codigo de postal
				-IdVia
				y devuelve en caso de error un parámetro de tipo String con el mensaje de error correspondiente

*/

	PROCEDURE OR REPLACE PROCEDURE insertar_direccion(
	pnumDir IN direcciones.numDir%TYPE,
	pletra IN direcciones.letra%TYPE,
	ppiso IN direcciones.piso%TYPE,
	pescalera IN direcciones.escalera%TYPE,
	pmano IN direcciones.mano%TYPE,
	pcp IN direcciones.cp%TYPE
	pidViaFK IN direcciones.idVia%TYPE,
	perror OUT varchar2(45))
	AS 
	
	BEGIN
		INSERT INTO DIRECCIONES VALUES (idDirecciones_seq.NEXTVAL,pnumDir, pletra, ppiso, pescalera, pmano, pcp, pidViaFK);
		COMMIT;
	
	EXCEPTION
	WHEN DUP_VAL_ON_INDEX THEN
	 perror:='Imposible insertar dirección.Se ha intentado duplicar la clave primaria';
	 rollback;
	WHEN OTHERS THEN
	perror:='Han Ocurrido Errores';
	rollback;
	
	END;