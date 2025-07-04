USE [master]
GO
/****** Object:  Database [bdLibreriaBazarRGE]    Script Date: 1/07/2025 22:32:23 ******/
CREATE DATABASE [bdLibreriaBazarRGE]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'bdLibreriaBazarRGE', FILENAME = N'C:\SQLData\bdLibreriaBazarRGE.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'bdLibreriaBazarRGE_log', FILENAME = N'C:\SQLData\bdLibreriaBazarRGE_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [bdLibreriaBazarRGE].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET ARITHABORT OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET  ENABLE_BROKER 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET  MULTI_USER 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET DB_CHAINING OFF 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET QUERY_STORE = ON
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [bdLibreriaBazarRGE]
GO
/****** Object:  Table [dbo].[categoria]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[categoria](
	[idCategoria] [smallint] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idCategoria] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[producto]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[producto](
	[idProducto] [smallint] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[descripcion] [varchar](100) NULL,
	[precioCompra] [decimal](8, 2) NOT NULL,
	[precioVenta] [decimal](8, 2) NOT NULL,
	[stock] [smallint] NOT NULL,
	[fechaVencimiento] [date] NULL,
	[idCategoria] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[idProducto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[vista_productos_completos]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[vista_productos_completos] AS
SELECT 
    p.idProducto,
    p.nombre,
    p.descripcion,
    p.precioCompra,
    p.precioVenta,
    p.stock,
    p.fechaVencimiento,
    c.idCategoria,
    c.nombre AS nombreCat
FROM 
    producto p
JOIN 
    categoria c ON p.idCategoria = c.idCategoria;
GO
/****** Object:  Table [dbo].[compra]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[compra](
	[idBoleta] [varchar](10) NOT NULL,
	[fecha] [date] NOT NULL,
	[total] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idBoleta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[detalleCompra]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[detalleCompra](
	[idDetalleCompra] [int] IDENTITY(1,1) NOT NULL,
	[idBoleta] [varchar](10) NULL,
	[idProducto] [smallint] NULL,
	[cantidad] [smallint] NOT NULL,
	[precioUnitario] [decimal](8, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idDetalleCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[detalleVenta]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[detalleVenta](
	[idDetalleVenta] [int] IDENTITY(1,1) NOT NULL,
	[idVenta] [int] NULL,
	[idItem] [smallint] NULL,
	[cantidad] [smallint] NOT NULL,
	[precioUnitario] [decimal](8, 2) NOT NULL,
	[tipoItem] [varchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idDetalleVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FormaPago]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FormaPago](
	[idFormaPago] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idFormaPago] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[servicio]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[servicio](
	[idServicio] [int] IDENTITY(1,1) NOT NULL,
	[tipo] [varchar](30) NOT NULL,
	[precio] [decimal](6, 2) NOT NULL,
	[fecha] [datetime] NOT NULL,
	[idCategoria] [smallint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idServicio] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[venta]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[venta](
	[idVenta] [int] IDENTITY(1,1) NOT NULL,
	[fecha] [datetime] NOT NULL,
	[total] [decimal](10, 2) NOT NULL,
	[idFormaPago] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[categoria] ON 

INSERT [dbo].[categoria] ([idCategoria], [nombre]) VALUES (1, N'Papelerías')
INSERT [dbo].[categoria] ([idCategoria], [nombre]) VALUES (2, N'Oficina')
INSERT [dbo].[categoria] ([idCategoria], [nombre]) VALUES (3, N'Cintas')
INSERT [dbo].[categoria] ([idCategoria], [nombre]) VALUES (4, N'Copias')
INSERT [dbo].[categoria] ([idCategoria], [nombre]) VALUES (5, N'Impresiones')
INSERT [dbo].[categoria] ([idCategoria], [nombre]) VALUES (6, N'Espiralados')
INSERT [dbo].[categoria] ([idCategoria], [nombre]) VALUES (7, N'Enmicados')
INSERT [dbo].[categoria] ([idCategoria], [nombre]) VALUES (8, N'Escaneos')
SET IDENTITY_INSERT [dbo].[categoria] OFF
GO
SET IDENTITY_INSERT [dbo].[detalleVenta] ON 

INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (1, 3, 3, 3, CAST(1.50 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (2, 4, 3, 2, CAST(1.50 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (3, 4, 1, 1, CAST(5.03 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (4, 4, 2, 2, CAST(5.00 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (5, 4, 4, 1, CAST(1.50 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (6, 4, 2, 1, CAST(5.00 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (7, 5, 1, 1, CAST(5.03 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (8, 5, 2, 1, CAST(5.00 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (9, 5, 3, 1, CAST(1.50 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (10, 5, 4, 1, CAST(1.50 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (11, 6, 2, 1, CAST(5.00 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (12, 6, 3, 3, CAST(1.50 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (13, 7, 1, 5, CAST(5.03 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (14, 8, 3, 2, CAST(1.50 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (15, 8, 1, 1, CAST(6.20 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (17, 10, 1, 3, CAST(6.20 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (18, 11, 4, 2, CAST(1.50 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (19, 11, 5, 1, CAST(3.20 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (20, 12, 7, 3, CAST(1.00 AS Decimal(8, 2)), N'Producto')
INSERT [dbo].[detalleVenta] ([idDetalleVenta], [idVenta], [idItem], [cantidad], [precioUnitario], [tipoItem]) VALUES (21, 12, 6, 2, CAST(0.80 AS Decimal(8, 2)), N'Producto')
SET IDENTITY_INSERT [dbo].[detalleVenta] OFF
GO
SET IDENTITY_INSERT [dbo].[FormaPago] ON 

INSERT [dbo].[FormaPago] ([idFormaPago], [descripcion]) VALUES (6, N'Culqi')
INSERT [dbo].[FormaPago] ([idFormaPago], [descripcion]) VALUES (1, N'Efectivo')
INSERT [dbo].[FormaPago] ([idFormaPago], [descripcion]) VALUES (3, N'Plin')
INSERT [dbo].[FormaPago] ([idFormaPago], [descripcion]) VALUES (5, N'Tarjeta')
INSERT [dbo].[FormaPago] ([idFormaPago], [descripcion]) VALUES (4, N'Transferencia')
INSERT [dbo].[FormaPago] ([idFormaPago], [descripcion]) VALUES (2, N'Yape')
SET IDENTITY_INSERT [dbo].[FormaPago] OFF
GO
SET IDENTITY_INSERT [dbo].[producto] ON 

INSERT [dbo].[producto] ([idProducto], [nombre], [descripcion], [precioCompra], [precioVenta], [stock], [fechaVencimiento], [idCategoria]) VALUES (1, N'Cuaderno Justus aA', N'Cuadriulado-100 Hojas', CAST(5.00 AS Decimal(8, 2)), CAST(7.00 AS Decimal(8, 2)), 22, NULL, 1)
INSERT [dbo].[producto] ([idProducto], [nombre], [descripcion], [precioCompra], [precioVenta], [stock], [fechaVencimiento], [idCategoria]) VALUES (2, N'tempera', N'apu amarilla', CAST(2.50 AS Decimal(8, 2)), CAST(5.00 AS Decimal(8, 2)), 0, NULL, 1)
INSERT [dbo].[producto] ([idProducto], [nombre], [descripcion], [precioCompra], [precioVenta], [stock], [fechaVencimiento], [idCategoria]) VALUES (3, N'Microporoso escarchado', N'azul', CAST(0.90 AS Decimal(8, 2)), CAST(1.50 AS Decimal(8, 2)), 0, NULL, 1)
INSERT [dbo].[producto] ([idProducto], [nombre], [descripcion], [precioCompra], [precioVenta], [stock], [fechaVencimiento], [idCategoria]) VALUES (4, N'Microporoso escarchado', N'rojo', CAST(0.90 AS Decimal(8, 2)), CAST(1.50 AS Decimal(8, 2)), 9, NULL, 1)
INSERT [dbo].[producto] ([idProducto], [nombre], [descripcion], [precioCompra], [precioVenta], [stock], [fechaVencimiento], [idCategoria]) VALUES (5, N'lapicero pilot azul', N'fine', CAST(1.70 AS Decimal(8, 2)), CAST(3.50 AS Decimal(8, 2)), 11, NULL, 2)
INSERT [dbo].[producto] ([idProducto], [nombre], [descripcion], [precioCompra], [precioVenta], [stock], [fechaVencimiento], [idCategoria]) VALUES (6, N'cinta azul', N'1.50cm', CAST(0.20 AS Decimal(8, 2)), CAST(0.80 AS Decimal(8, 2)), 8, NULL, 1)
INSERT [dbo].[producto] ([idProducto], [nombre], [descripcion], [precioCompra], [precioVenta], [stock], [fechaVencimiento], [idCategoria]) VALUES (7, N'lapiz', N'artezco', CAST(0.70 AS Decimal(8, 2)), CAST(1.00 AS Decimal(8, 2)), 9, NULL, 2)
INSERT [dbo].[producto] ([idProducto], [nombre], [descripcion], [precioCompra], [precioVenta], [stock], [fechaVencimiento], [idCategoria]) VALUES (8, N'Lapicero pilot rojo', N'fine', CAST(2.00 AS Decimal(8, 2)), CAST(3.20 AS Decimal(8, 2)), 12, NULL, 2)
SET IDENTITY_INSERT [dbo].[producto] OFF
GO
SET IDENTITY_INSERT [dbo].[venta] ON 

INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (3, CAST(N'2025-06-30T22:52:37.087' AS DateTime), CAST(4.50 AS Decimal(10, 2)), 1)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (4, CAST(N'2025-06-30T23:29:45.907' AS DateTime), CAST(24.53 AS Decimal(10, 2)), 1)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (5, CAST(N'2025-06-30T23:34:13.030' AS DateTime), CAST(13.03 AS Decimal(10, 2)), 1)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (6, CAST(N'2025-06-30T23:36:38.593' AS DateTime), CAST(9.50 AS Decimal(10, 2)), 1)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (7, CAST(N'2025-06-30T23:44:17.393' AS DateTime), CAST(25.15 AS Decimal(10, 2)), 6)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (8, CAST(N'2025-07-01T09:28:43.503' AS DateTime), CAST(9.20 AS Decimal(10, 2)), 1)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (9, CAST(N'2025-07-01T10:31:30.627' AS DateTime), CAST(18.60 AS Decimal(10, 2)), 3)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (10, CAST(N'2025-07-01T10:46:08.640' AS DateTime), CAST(18.60 AS Decimal(10, 2)), 6)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (11, CAST(N'2025-07-01T10:53:32.887' AS DateTime), CAST(6.20 AS Decimal(10, 2)), 1)
INSERT [dbo].[venta] ([idVenta], [fecha], [total], [idFormaPago]) VALUES (12, CAST(N'2025-07-01T19:10:26.510' AS DateTime), CAST(4.60 AS Decimal(10, 2)), 1)
SET IDENTITY_INSERT [dbo].[venta] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__FormaPag__298336B63F3DA438]    Script Date: 1/07/2025 22:32:23 ******/
ALTER TABLE [dbo].[FormaPago] ADD UNIQUE NONCLUSTERED 
(
	[descripcion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[detalleVenta] ADD  DEFAULT ('Producto') FOR [tipoItem]
GO
ALTER TABLE [dbo].[detalleCompra]  WITH CHECK ADD FOREIGN KEY([idBoleta])
REFERENCES [dbo].[compra] ([idBoleta])
GO
ALTER TABLE [dbo].[detalleCompra]  WITH CHECK ADD FOREIGN KEY([idProducto])
REFERENCES [dbo].[producto] ([idProducto])
GO
ALTER TABLE [dbo].[detalleVenta]  WITH CHECK ADD FOREIGN KEY([idItem])
REFERENCES [dbo].[producto] ([idProducto])
GO
ALTER TABLE [dbo].[detalleVenta]  WITH CHECK ADD FOREIGN KEY([idVenta])
REFERENCES [dbo].[venta] ([idVenta])
GO
ALTER TABLE [dbo].[producto]  WITH CHECK ADD FOREIGN KEY([idCategoria])
REFERENCES [dbo].[categoria] ([idCategoria])
GO
ALTER TABLE [dbo].[servicio]  WITH CHECK ADD FOREIGN KEY([idCategoria])
REFERENCES [dbo].[categoria] ([idCategoria])
GO
ALTER TABLE [dbo].[venta]  WITH CHECK ADD  CONSTRAINT [FK_Venta_FormaPago] FOREIGN KEY([idFormaPago])
REFERENCES [dbo].[FormaPago] ([idFormaPago])
GO
ALTER TABLE [dbo].[venta] CHECK CONSTRAINT [FK_Venta_FormaPago]
GO
/****** Object:  StoredProcedure [dbo].[sp_actualizar_stock]    Script Date: 1/07/2025 22:32:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_actualizar_stock]
    @idProducto INT,
    @nuevoStock INT
AS
BEGIN
    UPDATE producto
    SET stock = @nuevoStock
    WHERE idProducto = @idProducto;
END
GO
USE [master]
GO
ALTER DATABASE [bdLibreriaBazarRGE] SET  READ_WRITE 
GO
