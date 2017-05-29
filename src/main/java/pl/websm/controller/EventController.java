package pl.websm.controller;

import pl.websm.dao.EventCategoryDao;
import pl.websm.dao.EventCategoryDaoSqlite;
import pl.websm.dao.EventDao;
import pl.websm.dao.EventDaoSqlite;
import pl.websm.model.Event;
import pl.websm.model.EventCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventController {

    public static ModelAndView renderEvents(Request request, Response response) {
        Map params = new HashMap<>();
        EventDao eventDao = new EventDaoSqlite();
        EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();
        params.put("events", eventDao.getAll());
        params.put("categories", eventCategoryDao.getAll());
        return new ModelAndView(params, "index");
    }

    public static ModelAndView renderEventDetails(Request request, Response response) {
        Map params = new HashMap<>();
        EventDao eventDao = new EventDaoSqlite();
        Event event = eventDao.find(Integer.parseInt(request.params(":id")));
        if (event == null) {
            return EventController.renderEvents(request, response);
        }
        params.put("event", event);
        return new ModelAndView(params, "event/description");
    }

    public static ModelAndView sortEvents(Request request, Response response) {
        Map params = new HashMap<>();
        EventDao eventDao = new EventDaoSqlite();
        EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();
        List<Event> events = eventDao.getBy(
                eventCategoryDao.find(
                        Integer.parseInt(
                                request.queryParams("category"))));
        params.put("events", events);
        params.put("categories", eventCategoryDao.getAll());
        return new ModelAndView(params, "index");
    }

    public static ModelAndView renderAddEvent(Request request, Response response) {
        Map params = new HashMap<>();
        EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();
        params.put("categories", eventCategoryDao.getAll());
        return new ModelAndView(params, "event/add_event");
    }

    public static ModelAndView addNew(Request request, Response response) {
        EventDao eventDao = new EventDaoSqlite();
        EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();
        String name = request.queryParams("name");
        String date = request.queryParams("date");
        String description = request.queryParams("description");
        String link = request.queryParams("link");
        if (name.isEmpty() || date.isEmpty() || description.isEmpty() || link.isEmpty() || date.length() > 10) {
            return AdminController.renderAdminInterface(request, response);
        }
        Event event = new Event(name,
                date,
                description,
                eventCategoryDao.find(
                        Integer.parseInt(
                                request.queryParams("category"))),
                link);
        eventDao.save(event);
        return AdminController.renderAdminInterface(request, response);
    }

    public static ModelAndView renderEditEvent(Request request, Response response) {
        Map params = new HashMap<>();
        EventDao eventDao = new EventDaoSqlite();
        EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();
        Event event = eventDao.find(Integer.parseInt(request.params(":id")));
        if (event == null) {
            return AdminController.renderAdminInterface(request, response);
        }
        params.put("event", event);
        params.put("categories", eventCategoryDao.getAll());
        return new ModelAndView(params, "event/edit_event");
    }

    public static ModelAndView editEvent(Request request, Response response) {
        EventDao eventDao = new EventDaoSqlite();
        EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();
        EventCategory eventCategory;

        Event event = eventDao.find(Integer.parseInt(request.params(":id")));

        String name = request.queryParams("name");
        name = (name.isEmpty()) ? event.getName() : name;

        String date = request.queryParams("date");
        date = (date.isEmpty() || date.length() > 10) ? event.getDate() : date;

        String description = request.queryParams("description");
        description = (description.isEmpty()) ? event.getDescription() : description;

        eventCategory = eventCategoryDao.find(
                Integer.parseInt(
                        request.queryParams("category")));

        String link = request.queryParams("link");
        link = (link.isEmpty()) ? event.getLink() : link;
        eventDao.save(
                new Event(event.getId(), name, date,  description, eventCategory, link));
        return AdminController.renderAdminInterface(request, response);
    }

    public static ModelAndView removeEvent(Request request, Response response) {
        Map params = new HashMap<>();
        EventDao eventDao = new EventDaoSqlite();
        Event event = eventDao.find(Integer.parseInt(request.params(":id")));
        if (event == null) {
            return AdminController.renderAdminInterface(request, response);
        }
        eventDao.remove(event.getId());
        return AdminController.renderAdminInterface(request, response);
    }

    public static ModelAndView searchEngine(Request request, Response response) {
        Map params = new HashMap<>();
        EventDao eventDao = new EventDaoSqlite();
        EventCategoryDao eventCategoryDao = new EventCategoryDaoSqlite();
        String name = request.queryParams("name");
        if (name.length() < 3) {
            return EventController.renderEvents(request, response);
        }
        List<Event> events = eventDao.getBy(name);
        params.put("events", events);
        params.put("categories", eventCategoryDao.getAll());
        return new ModelAndView(params, "index");
    }
}