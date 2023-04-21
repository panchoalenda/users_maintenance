package org.falenda.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos{
    private static final String url = "jdbc:mysql://localhost:3306/usuarios?serveTimezone=Europe/Madrid";
    private static final String user = "root";
    private static final String password = "1234";
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
