package pl.websm.dao;

import pl.websm.model.Event;
import pl.websm.model.EventCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDaoSqlite implements EventDao{

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @Override
    public void save(Event event) {
        try{
            connection = SQLiteJDBCConnector.connection();

            if (event.getId() != null){
                statement = connection.prepareStatement(
                        "UPDATE events SET " +
                                "name=(?), " +
                                "date=(?), " +
                                "description=(?), " +
                                "category_id=(?), " +
                                "link=(?) " +
                                "WHERE id =(?);");
                statement.setInt(6, event.getId());
            } else {
                statement = connection.prepareStatement(
                        "INSERT INTO events " +
                                "(name, date, description,  category_id, link)" +
                                "VALUES ((?), (?), (?), (?), (?))");
            }
            statement.setString(1, event.getName());
            statement.setString(2, event.getDate());
            statement.setString(3, event.getDescription());
            statement.setInt(4, event.getEventCategory().getId());
            statement.setString(5, event.getLink());
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Event find(Integer id) {
        Event event = null;

        try {
            connection = SQLiteJDBCConnector.connection();
            statement = connection.prepareStatement("SELECT * FROM events WHERE id=(?)");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("description"),
                        new EventCategoryDaoSqlite().find(
                                resultSet.getInt("category_id")),
                        resultSet.getString("link"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public void remove(Integer id) {
        try {
            connection = SQLiteJDBCConnector.connection();
            statement = connection.prepareStatement(
                    "DELETE FROM events WHERE id=(?)");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();

        try {
            connection = SQLiteJDBCConnector.connection();
            statement = connection.prepareStatement("SELECT * FROM events");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("description"),
                        new EventCategoryDaoSqlite().find(
                                resultSet.getInt("category_id")),
                        resultSet.getString("link"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public List<Event> getBy(EventCategory eventCategory) {
        List<Event> events = new ArrayList<>();

        try {
            connection = SQLiteJDBCConnector.connection();
            statement = connection.prepareStatement("SELECT * FROM events WHERE category_id=(?)");
            statement.setInt(1, eventCategory.getId());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("description"),
                        new EventCategoryDaoSqlite().find(
                                resultSet.getInt("category_id")),
                        resultSet.getString("link"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public List<Event> getBy(String name) {
        List<Event> events = new ArrayList<>();
        try {
            connection = SQLiteJDBCConnector.connection();
            statement = connection.prepareStatement("SELECT * FROM events WHERE name LIKE (?)");
            statement.setString(1,'%' + name + '%');
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("description"),
                        new EventCategoryDaoSqlite().find(
                                resultSet.getInt("category_id")),
                        resultSet.getString("link"));
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }
}