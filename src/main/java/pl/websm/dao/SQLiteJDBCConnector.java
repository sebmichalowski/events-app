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

    public static void checkParameters(String[] args){
        if (args.length > 0 && args[0].equals("--init-db")) {
            try {
                createTables();
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else if (args.length > 0 && args[0].equals("--migrate-db")) {
            try {
                insertToDb();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private static void insertToDb() throws SQLException {
        connection = connection();
        statement = connection.createStatement();
        statement.execute("INSERT INTO events (name, date, description, category, link) \n" +
                "VALUES ('Event 1', '2022-01-01', 'First event', 1, 'https://www.codecool.pl/'), \n" +
                "('Event 2', '2023-02-02', 'Description for second event', 2, 'https://websm.pl');");
        statement.execute("INSERT INTO categories(name)" +
                "VALUES ('Category One')," +
                "       ('Category Two')," +
                "       ('Category Three')," +
                "       ('Category Four')");
        System.out.println("Data inserted");
    }
}