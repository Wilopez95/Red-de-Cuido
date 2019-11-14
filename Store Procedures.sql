---------------------------------------------------------------------------------------------- Login Admin----------------------------------------------------------------------------------------------
CREATE PROCEDURE Login_Admin
	@User varchar(50),
	@Pass varchar(50)
AS
BEGIN
	DECLARE @IDEmpleado int
	BEGIN TRY
		BEGIN TRAN
	
			SELECT @IDEmpleado = FK_Empleado FROM Administrador ad WHERE ad.Correo = @User AND ad.Contraseña = @Pass 
			IF @IDEmpleado IS NOT NULL
				BEGIN
					SELECT dbo.Administrador.Correo AS Correo,dbo.Administrador.Tipo_Admin AS Tipo, dbo.Centro.Nombre AS Centro, dbo.Empleado.Nombre AS Nombre
					FROM     dbo.Administrador INNER JOIN
							 dbo.Empleado ON dbo.Administrador.FK_Empleado = dbo.Empleado.ID INNER JOIN
							 dbo.Centro ON dbo.Empleado.FK_Centro = dbo.Centro.ID
							 WHERE dbo.Empleado.ID = @IDEmpleado 
				END
		COMMIT
			IF @IDEmpleado IS NULL
				BEGIN
					RETURN -1
				END
	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END
----------------------------------------------------------------------------------------------Login Cliente----------------------------------------------------------------------------------------------
CREATE PROCEDURE Login_User
	@User varchar(50),
	@Pass varchar(50)
AS
BEGIN
	DECLARE @IDCliente int
	BEGIN TRY
		BEGIN TRAN
	
			SELECT @IDCliente = ci.ID FROM Cliente ci WHERE ci.Correo = @User AND ci.Contraseña = @Pass AND ci.Estado=1
			IF @IDCliente IS NOT NULL
				BEGIN
					SELECT cl.Nombre,cl.Correo , cl.Calificacion FROM Cliente cl WHERE cl.ID = @IDCliente
				END
		COMMIT
			IF @IDCliente IS NULL
				BEGIN
					RETURN -1
				END
	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END
----------------------------------------------------------------------------------------------Registrar Cliente----------------------------------------------------------------------------------------------
CREATE PROCEDURE Register_User
	@Name varchar(50),
	@User varchar(50),
	@Pass varchar(50),
	@Justification varchar(240)
AS
BEGIN
	DECLARE @IDCliente int
	BEGIN TRY
		BEGIN TRAN
	
			SELECT @IDCliente = cl.ID FROM Cliente cl WHERE cl.Correo = @User
			IF @IDCliente IS NULL
				BEGIN
					INSERT INTO Cliente(Nombre,Correo,Contraseña,Justificacion,Estado,Calificacion)VALUES(@Name,@User,@Pass,@Justification,0,5)
				END
		COMMIT
			IF @IDCliente IS NOT NULL
				BEGIN
					RETURN -1
				END
	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

----------------------------------------------------------------------------------------------Asociar enfermedad cliente----------------------------------------------------------------------------------------------
CREATE PROCEDURE Register_Disease
	@MailCliente varchar(50),
	@Disease varchar(50),
	@Medication varchar(50)
	
AS
BEGIN
	DECLARE @IDCliente int
	BEGIN TRY
		BEGIN TRAN
			SELECT @IDCliente = cl.ID FROM Cliente cl WHERE cl.Correo = @MailCliente
			IF @IDCliente IS NOT NULL
				BEGIN
					INSERT INTO Mapeo_Cliente(FK_Cliente,Enfermedad,Tratamiento)VALUES(@IDCliente,@Disease,@Medication)
				END
		COMMIT
			IF @IDCliente IS NULL
				BEGIN
					RETURN -1
				END
	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Obtener Empleados--------------------------------------------------------------------------------
CREATE PROCEDURE Get_Empleados
AS
BEGIN
	BEGIN TRY
		BEGIN TRAN
			SELECT dbo.Centro.Nombre AS Centro, dbo.Empleado.ID, dbo.Empleado.Nombre AS Nombre, dbo.Empleado.Calificacion, dbo.Puesto.Nombre_Puesto, dbo.Tipo_Empleado.Descripcion AS Tipo
			FROM     dbo.Centro INNER JOIN
                  dbo.Empleado ON dbo.Centro.ID = dbo.Empleado.FK_Centro INNER JOIN
                  dbo.Puesto ON dbo.Empleado.FK_Puesto = dbo.Puesto.ID INNER JOIN
                  dbo.Tipo_Empleado ON dbo.Empleado.FK_Tipo_Empleado = dbo.Tipo_Empleado.ID

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Obtener Cuidadores--------------------------------------------------------------------------------
CREATE PROCEDURE Get_Cuidadores
AS
BEGIN
	BEGIN TRY
		BEGIN TRAN
			SELECT dbo.Centro.Nombre AS Centro, dbo.Empleado.ID, dbo.Empleado.Nombre AS Nombre, dbo.Empleado.Calificacion, dbo.Puesto.Nombre_Puesto, dbo.Tipo_Empleado.Descripcion AS Tipo
			FROM     dbo.Centro INNER JOIN
                  dbo.Empleado ON dbo.Centro.ID = dbo.Empleado.FK_Centro INNER JOIN
                  dbo.Puesto ON dbo.Empleado.FK_Puesto = dbo.Puesto.ID INNER JOIN
                  dbo.Tipo_Empleado ON dbo.Empleado.FK_Tipo_Empleado = dbo.Tipo_Empleado.ID
				  WHERE dbo.Tipo_Empleado.Descripcion= 'Cuidador'

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END
------------------------------------------------------------------------------------------------Obtener Horarios--------------------------------------------------------------------------------
CREATE PROCEDURE Get_Horarios 
	@IdEmpleado int
AS
BEGIN
	BEGIN TRY
		BEGIN TRAN

			SELECT h.Dia, h.Hora_Inicio, h.Hora_Final
			FROM (Empleado ep LEFT JOIN EmpleadoxHorario exh ON ep.ID=exh.FK_Empleado)
			LEFT JOIN Horario h ON exh.FK_Horario = h.ID
			WHERE ep.ID=@IdEmpleado

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Obtener Clientes--------------------------------------------------------------------------------
CREATE PROCEDURE Get_Clientes
AS
BEGIN
	BEGIN TRY
		BEGIN TRAN

			SELECT Nombre, Correo, Calificacion
			FROM Cliente

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Obtener Enfermedades--------------------------------------------------------------------------------
CREATE PROCEDURE Get_Enfermedades  
	@User varchar(50)
AS
BEGIN
	DECLARE @IDCliente int
	BEGIN TRY
		BEGIN TRAN
			SELECT @IDCliente = cl.ID FROM Cliente cl WHERE cl.Correo = @User
			IF @IDCliente IS NOT NULL
			BEGIN
				SELECT mc.Enfermedad, mc.Tratamiento
				FROM Cliente cl LEFT JOIN Mapeo_Cliente mc ON cl.ID = mc.FK_Cliente
				WHERE cl.ID=@IdCliente
			END

		COMMIT
			IF @IDCliente IS NULL
				BEGIN
					RETURN -1
				END
	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Obtener Centros--------------------------------------------------------------------------------
CREATE PROCEDURE Get_Centros
AS
BEGIN
	BEGIN TRY
		BEGIN TRAN

			SELECT Nombre, Ubicacion
			FROM Centro

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Obtener Servicios--------------------------------------------------------------------------------
CREATE PROCEDURE Get_Servicios
AS
BEGIN
	BEGIN TRY
		BEGIN TRAN

			SELECT Nombre
			FROM Servicio

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Obtener Contratos--------------------------------------------------------------------------------
CREATE PROCEDURE Get_Contratos
@IdEmpleado int,
@Correo_Cliente varchar(50)
AS
BEGIN
	BEGIN TRY
		BEGIN TRAN

			SELECT ct.ID, clt.Nombre as 'Cliente', ep.Nombre as 'Empleado', sv.Nombre as 'Servicio', ct.Fecha_inicio, ct.Fecha_final, ct.Calificacion_Empleado, 
			ct.Comentario_Empleado, ct.Calificacion_Cliente, ct.Comentario_Cliente, ct.horasdiarias, 
			SUM(DATEDIFF(day,ct.Fecha_inicio, ct.Fecha_final)*ct.horasdiarias*cat.Precio_Cliente) AS 'Total'

			FROM ((Contrato ct LEFT JOIN Cliente clt ON ct.FK_Cliente = clt.ID)
			LEFT JOIN Empleado ep ON ct.FK_Empleado = ep.ID
			LEFT JOIN Servicio sv ON ct.FK_Servicio = sv.ID)
			LEFT JOIN Categoria cat ON sv.FK_Categoria = cat.ID

			WHERE (ep.ID = @IdEmpleado OR @IdEmpleado IS NULL)
			AND (clt.Correo = @Correo_Cliente OR @Correo_Cliente IS NULL)

			GROUP BY ct.ID, clt.Nombre, ep.Nombre, sv.Nombre, ct.Fecha_inicio, ct.Fecha_final, ct.Calificacion_Empleado, 
			ct.Comentario_Empleado, ct.Calificacion_Cliente, ct.Comentario_Cliente, ct.horasdiarias
			

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Obtener Historial--------------------------------------------------------------------------------
CREATE
CREATE PROCEDURE Get_Historial
@Correo_Cliente varchar(50)
AS
BEGIN
	DECLARE @IDCliente int
	BEGIN TRY
		BEGIN TRAN
			SELECT @IDCliente = cl.ID FROM Cliente cl WHERE cl.Correo = @Correo_Cliente
			IF @IDCliente IS NOT NULL
				BEGIN 
						SELECT ct.ID, clt.Nombre as 'Cliente', ep.Nombre as 'Empleado', sv.Nombre as 'Servicio', ct.Fecha_inicio, ct.Fecha_final, ct.Calificacion_Empleado, 
						ct.Comentario_Empleado, ct.Calificacion_Cliente, ct.Comentario_Cliente, ct.horasdiarias, 
						SUM(DATEDIFF(day,ct.Fecha_inicio, ct.Fecha_final)*ct.horasdiarias*cat.Precio_Cliente) AS 'Total'

						FROM ((Contrato ct LEFT JOIN Cliente clt ON ct.FK_Cliente = clt.ID)
						LEFT JOIN Empleado ep ON ct.FK_Empleado = ep.ID
						LEFT JOIN Servicio sv ON ct.FK_Servicio = sv.ID)
						LEFT JOIN Categoria cat ON sv.FK_Categoria = cat.ID

						WHERE clt.ID = @IDCliente
					

						GROUP BY ct.ID, clt.Nombre, ep.Nombre, sv.Nombre, ct.Fecha_inicio, ct.Fecha_final, ct.Calificacion_Empleado, 
						ct.Comentario_Empleado, ct.Calificacion_Cliente, ct.Comentario_Cliente, ct.horasdiarias
			
				END			
		
		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END


------------------------------------------------------------------------------------------------Agregar Contratos--------------------------------------------------------------------------------
CREATE PROCEDURE Agregar_Contrato
@IdEmpleado int,
@Correo_Cliente varchar(50),
@Servicio varchar(50),
@Fecha_inicial date,
@Fecha_final date,
@Horas int

AS
BEGIN
	DECLARE @IdCliente int
	DECLARE @IdServicio int
	BEGIN TRY
		BEGIN TRAN
			
			SELECT @IdServicio= ID FROM Servicio WHERE Nombre = @Servicio
			SELECT @IdCliente= ID FROM Cliente WHERE Correo = @Correo_Cliente

			INSERT INTO Contrato(FK_Cliente, FK_Empleado, FK_Servicio, Fecha_inicio, Fecha_final, horasdiarias, Calificacion_Empleado, Comentario_Empleado, Calificacion_Cliente, Comentario_Cliente)
			VALUES(@IdCliente, @IdEmpleado, @IdServicio, @Fecha_inicial, @Fecha_final, @Horas, 0, '-', 0, '-')

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END

------------------------------------------------------------------------------------------------Habilitar Cliente--------------------------------------------------------------------------------
CREATE PROCEDURE Enable_Cliente
	@User varchar(50)
AS
BEGIN
	DECLARE @IDCliente int
	BEGIN TRY
		BEGIN TRAN
	
			SELECT @IDCliente = ci.ID FROM Cliente ci WHERE ci.Correo = @User
			IF @IDCliente IS NOT NULL
				BEGIN
					UPDATE Cliente SET Estado = 1 WHERE Cliente.ID = @IDCliente
				END
		COMMIT
			IF @IDCliente IS NULL
				BEGIN
					RETURN -1
				END
	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END
------------------------------------------------------------------------------------------------Calificar Empleado--------------------------------------------------------------------------------
CREATE PROCEDURE Calificar
@Calificacion_Empleado int,
@Comentario_Empleado varchar(50),
@Calificacion_Cliente int,
@Comentario_Cliente varchar(50),
@IdContrato int

AS
BEGIN
	DECLARE @IdCliente int
	DECLARE @AVGcalificacion_cliente int
	DECLARE @IdEmpleado int
	DECLARE @AVGcalificacion_empleado int
	BEGIN TRY
		BEGIN TRAN
			
			IF @Calificacion_Empleado IS NOT NULL
				BEGIN

					UPDATE Contrato
					SET Calificacion_Empleado = @Calificacion_Empleado
					WHERE ID=@IdContrato

					SELECT @IdEmpleado = FK_Empleado FROM Contrato WHERE ID = @IdContrato

					SELECT @AVGcalificacion_empleado = AVG(Calificacion_Empleado) FROM Contrato WHERE FK_Empleado = @IdEmpleado

					UPDATE Empleado
					SET Calificacion = @AVGcalificacion_empleado
					WHERE ID = @IdEmpleado

				END

			IF @Calificacion_Cliente IS NOT NULL
				BEGIN 

					UPDATE Contrato
					SET Calificacion_Cliente = @Calificacion_Cliente
					WHERE ID = @IdContrato

					SELECT @IdCliente= FK_Cliente FROM Contrato WHERE ID = @IdContrato

					SELECT @AVGcalificacion_cliente = AVG(Calificacion_Cliente) FROM Contrato WHERE FK_Cliente = @IdCliente

					UPDATE Cliente
					SET Calificacion = @AVGcalificacion_cliente
					WHERE ID = @IdCliente

				END

			IF @Comentario_Empleado IS NOT NULL
				BEGIN

					UPDATE Contrato
					SET Comentario_Empleado = @Comentario_Empleado
					WHERE ID = @IdContrato

				END
				
			IF @Comentario_Cliente IS NOT NULL
				BEGIN

					UPDATE Contrato
					SET Comentario_Cliente = @Comentario_Cliente
					WHERE ID = @IdContrato

				END

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END
------------------------------------------------------------------------------------------------Calificar Cliente--------------------------------------------------------------------------------
CREATE PROCEDURE Calificar_Clientes
@Calificacion_Cliente int,
@Comentario_Cliente varchar(50),
@IdContrato int

AS
BEGIN
	DECLARE @IdCliente int
	DECLARE @AVGcalificacion_cliente int
	BEGIN TRY
		BEGIN TRAN
			

			IF @Calificacion_Cliente IS NOT NULL
				BEGIN 

					UPDATE Contrato
					SET Calificacion_Cliente = @Calificacion_Cliente
					WHERE ID = @IdContrato

					SELECT @IdCliente= FK_Cliente FROM Contrato WHERE ID = @IdContrato

					SELECT @AVGcalificacion_cliente = AVG(Calificacion_Cliente) FROM Contrato WHERE FK_Cliente = @IdCliente

					UPDATE Cliente
					SET Calificacion = @AVGcalificacion_cliente
					WHERE ID = @IdCliente

				END

				
			IF @Comentario_Cliente IS NOT NULL
				BEGIN

					UPDATE Contrato
					SET Comentario_Cliente = @Comentario_Cliente
					WHERE ID = @IdContrato

				END

		COMMIT

	END TRY
	BEGIN CATCH
		ROLLBACK
	END CATCH
END
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------
