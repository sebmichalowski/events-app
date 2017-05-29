package pl.websm.dao;

import pl.websm.model.Event;
import pl.websm.model.EventCategory;

import java.util.List;

public interface EventDao {

    void save(Event event);
    Event find(Integer id);
    void remove(Integer id);

    List<Event> getAll();
    List<Event> getBy(EventCategory eventCategory);
    List<Event> getBy(String name);
}