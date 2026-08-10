package gt.edu.umg.sistema.estudiantes.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexionMySQL {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    public static final String DATABASE = "sistema_estudiantes_AJ";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static final String PARAMETERS = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String URL_BASE = "jdbc:mysql://" + HOST + ":" + PORT + "/" + PARAMETERS;
    private static final String URL_DATABASE = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + PARAMETERS;
    private static final Object LOCK = new Object();
    private static volatile boolean initialized;

    private ConexionMySQL() {
    }

    public static Connection getConnection() throws SQLException {
        inicializar();
        return DriverManager.getConnection(URL_DATABASE, USER, PASSWORD);
    }

    public static void inicializar() throws SQLException {
        if (initialized) {
            return;
        }

        synchronized (LOCK) {
            if (initialized) {
                return;
            }

            try (Connection conn = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(
                        "CREATE DATABASE IF NOT EXISTS `" + DATABASE + "` "
                                + "CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci"
                );
            }

            try (Connection conn = DriverManager.getConnection(URL_DATABASE, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS estudiantes ("
                                + "id INT NOT NULL PRIMARY KEY,"
                                + "nombres VARCHAR(100) NOT NULL,"
                                + "apellidos VARCHAR(100) NOT NULL,"
                                + "email VARCHAR(120) NOT NULL,"
                                + "carnet VARCHAR(50) NOT NULL"
                                + ") ENGINE=InnoDB"
                );

                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS facturas ("
                                + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                                + "numero_factura VARCHAR(50) NOT NULL UNIQUE,"
                                + "fecha_factura DATE NOT NULL,"
                                + "nombre_cliente VARCHAR(120) NOT NULL,"
                                + "nit_cliente VARCHAR(50) NOT NULL,"
                                + "direccion_cliente VARCHAR(200) NOT NULL,"
                                + "total DECIMAL(12,2) NOT NULL,"
                                + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"
                                + ") ENGINE=InnoDB"
                );

                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS factura_detalles ("
                                + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                                + "factura_id BIGINT NOT NULL,"
                                + "producto VARCHAR(150) NOT NULL,"
                                + "cantidad INT NOT NULL,"
                                + "precio DECIMAL(12,2) NOT NULL,"
                                + "subtotal DECIMAL(12,2) NOT NULL,"
                                + "CONSTRAINT fk_factura_detalle_factura "
                                + "FOREIGN KEY (factura_id) REFERENCES facturas(id) ON DELETE CASCADE"
                                + ") ENGINE=InnoDB"
                );
            }

            initialized = true;
        }
    }
}
