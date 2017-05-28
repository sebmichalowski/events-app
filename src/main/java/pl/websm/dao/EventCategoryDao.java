package pl.websm.dao;

import pl.websm.model.EventCategory;

import java.util.List;

public interface EventCategoryDao {

    EventCategory find(Integer id);

    List<EventCategory> getAll();
}
