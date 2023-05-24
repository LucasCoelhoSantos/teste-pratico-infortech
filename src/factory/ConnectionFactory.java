package factory;

import util.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // Conexão com o banco de dados
    public static Connection createConnectionToMySQL() throws SQLException, ClassNotFoundException {
    	Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DatabaseConfig.getDatabaseUrl(), DatabaseConfig.getUsername(), DatabaseConfig.getPassword());
        return connection;
    }
}
