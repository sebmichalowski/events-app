package pl.websm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteJDBCConnector {

    private static Connection connection;
    private static Statement statement;

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

    private static void createTables()throws SQLException {
        connection = connection();
        statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS events \n" +
                        "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "date TEXT," +
                        "description TEXT," +
                        "category_id INTEGER," +
                        "link TEXT" +
                        ")");
        statement.execute(
                "CREATE TABLE IF NOT EXISTS categories" +
                        "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT\n" +
                        ")");
        System.out.println("Database tables created.");
    }
}
