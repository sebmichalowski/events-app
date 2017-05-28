package pl.websm.dao;

import pl.websm.model.EventCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventCategoryDaoSqlite implements EventCategoryDao{

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @Override
    public EventCategory find(Integer id) {
        EventCategory eventCategory = null;

        try {
            connection = SQLiteJDBCConnector.connection();
            statement = connection.prepareStatement("SELECT * FROM categories WHERE id=(?)");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                eventCategory = new EventCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"));
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventCategory;
    }

    @Override
    public List<EventCategory> getAll() {
        List<EventCategory> categories = new ArrayList<>();

        try {
            connection = SQLiteJDBCConnector.connection();
            statement = connection.prepareStatement("SELECT * FROM categories");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EventCategory eventCategory = new EventCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"));
                categories.add(eventCategory);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}