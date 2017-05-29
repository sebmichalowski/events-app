package pl.websm.controller;

import pl.websm.dao.EventCategoryDao;
import pl.websm.dao.EventCategoryDaoSqlite;
import pl.websm.dao.EventDao;
import pl.websm.dao.EventDaoSqlite;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class AdminController {

    public static ModelAndView renderAdminInterface(Request request, Response response){
        Map params = new HashMap();
        EventDao eventDao = new EventDaoSqlite();
        EventCategoryDao eventCategory = new EventCategoryDaoSqlite();
        params.put("events", eventDao.getAll());
        params.put("categories", eventCategory.getAll());
        return new ModelAndView(params, "admin");
    }
}
