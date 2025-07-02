package CapaDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Connection conn;

    public static Connection conectar() {
        try {
            if (conn == null || conn.isClosed()) {
                
                String url = "jdbc:sqlserver://localhost:1433;"
                           + "databaseName=bdLibreriaBazarRGE;"
                           + "integratedSecurity=true;"
                           + "encrypt=true;"
                           + "trustServerCertificate=true";

                conn = DriverManager.getConnection(url);
                System.out.println("Conexión exitosa");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conn;
    }
}
