
CREATE TABLE [dbo].[Centro](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[Nombre] [varchar](50) NULL,
	[Ubicacion] [varchar](50) NULL,
)
CREATE TABLE [dbo].[Tipo_Empleado](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[Descripcion] [varchar](50) NULL,
)
CREATE TABLE [dbo].[Puesto](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[Nombre_Puesto] [varchar](50) NULL,
	[Salario] [money] NULL,
)
CREATE TABLE [dbo].[Actividad](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[Nombre] [varchar](50) NULL,
	[Descripcion] [varchar](200) NULL,
)
CREATE TABLE [dbo].[ActividadxPuesto](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[FK_Actividad] int FOREIGN KEY REFERENCES Actividad(ID),
	[FK_Puesto] int FOREIGN KEY REFERENCES Puesto(ID),
)
CREATE TABLE [dbo].[Horario](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[Dia] [varchar](15) NULL,
	[Hora_Inicio] [time](7) NULL,
	[Hora_Final] [time](7) NULL,
)
CREATE TABLE [dbo].[Empleado](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[FK_Centro] int FOREIGN KEY REFERENCES Centro(ID),
	[FK_Puesto] int FOREIGN KEY REFERENCES Puesto(ID),
	[FK_Tipo_Empleado]int FOREIGN KEY REFERENCES Tipo_Empleado(ID),
	[Nombre] [varchar](50) NULL,
	[Calificacion] int DEFAULT 5
)
CREATE TABLE [dbo].[EmpleadoxHorario](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[FK_Empleado] int FOREIGN KEY REFERENCES Empleado(ID),
	[FK_Horario] int FOREIGN KEY REFERENCES Horario(ID),
)

CREATE TABLE [dbo].[Administrador](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[FK_Empleado] int FOREIGN KEY REFERENCES Empleado(ID),
	[Tipo_Admin] int DEFAULT 0,
	[Correo] [varchar](50) NULL,
	[Contraseña] [varchar](50) NULL,
)
CREATE TABLE [dbo].[Categoria](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[Nombre] [varchar](50) NULL,
	[Precio_Cliente] [money] NULL,
	[Precio_Cuidador] [money] NULL,
)
CREATE TABLE [dbo].[EmpleadoxCategoria](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[FK_Empleado] int FOREIGN KEY REFERENCES Empleado(ID),
	[FK_Categoria] int FOREIGN KEY REFERENCES Puesto(ID),
)
CREATE TABLE [dbo].[Servicio](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[FK_Categoria] int FOREIGN KEY REFERENCES Categoria(ID),
	[Nombre] [varchar](50) NULL,
)
CREATE TABLE [dbo].[Cliente](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[Nombre] [varchar](50) NULL,
	[Correo] [varchar](50) NULL,
	[Contraseña] [varchar](50) NULL,
	[Justificacion] [varchar](200) NULL,
	[Estado] [bit] NULL,
	[Calificacion] [int] NULL,
)
CREATE TABLE [dbo].[Mapeo_Cliente](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[FK_Cliente] int FOREIGN KEY REFERENCES Cliente(ID),
	[Enfermedad] [varchar](50) NULL,
	[Tratamiento] [varchar](50) NULL,
)
CREATE TABLE [dbo].[Contrato](
	[ID] int IDENTITY(1,1) PRIMARY KEY,
	[FK_Cliente] int FOREIGN KEY REFERENCES Cliente(ID),
	[FK_Empleado] int FOREIGN KEY REFERENCES Empleado(ID),
	[FK_Servicio] int FOREIGN KEY REFERENCES Servicio(ID),
	[Fecha_inicio] [date] NULL,
	[Fecha_final] [date] NULL,
	[horasdiarias] int Null,
	[Calificacion_Empleado] [int] NULL,
	[Comentario_Empleado] [varchar](250) NULL,
	[Calificacion_Cliente] [int] NULL,
	[Comentario_Cliente] [varchar](250) NULL,
)







