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
}