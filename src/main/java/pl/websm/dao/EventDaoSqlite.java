package pl.websm.dao;

import pl.websm.model.Event;
import pl.websm.model.EventCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EventDaoSqlite implements EventDao{

    private Connection connection;
    private PreparedStatement statement;

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
            statement.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Event find(Integer id) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Event> getAll() {
        return null;
    }

    @Override
    public List<Event> getBy(EventCategory eventCategory) {
        return null;
    }
}
