package pl.websm.dao;

import pl.websm.model.EventCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventCategory;
    }

    @Override
    public List<EventCategory> getAll() {
        return null;
    }
}