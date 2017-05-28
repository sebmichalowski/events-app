package pl.websm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteJDBCConnector {

    static Connection connection() {
        Connection connection = null;
        try {
            String url = "jdbc:sqlite:src/main/resources/database.db";
            connection = DriverManager.getConnection(url); // create a connection to the database
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return connection;
    }
}
