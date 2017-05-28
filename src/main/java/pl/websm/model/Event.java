package pl.websm.model;

public class Event extends BaseModel {

    private String description;
    private String date;
    private EventCategory eventCategory;
    private String link;

    public Event(Integer id, String name, String description, String date, EventCategory eventCategory, String link){
        super(id, name);
        setDescription(description);
        setDate(date);
        setEventCategory(eventCategory);
        setLink(link);
    }

    public Event(String name, String description, String date, EventCategory eventCategory, String link){
        super(name);
        setDescription(description);
        setDate(date);
        setEventCategory(eventCategory);
        setLink(link);
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    private void setDate(String date) {
        this.date = date;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    private void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getLink() {
        return link;
    }

    private void setLink(String link) {
        this.link = link;
    }
}