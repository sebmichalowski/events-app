package pl.websm.model;

public class EventCategory extends BaseModel {

    public EventCategory(Integer id, String name) {
        super(id, name);
    }

    public EventCategory(String name) {
        super(name);
    }
}