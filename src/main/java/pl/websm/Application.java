package pl.websm;

import pl.websm.controller.AdminController;
import pl.websm.controller.EventController;
import pl.websm.dao.SQLiteJDBCConnector;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;
import static spark.Spark.get;

class Application {

    static void run(String[] args){
        SQLiteJDBCConnector.checkParameters(args);
        dispatchRoutes();
    }

    private static void dispatchRoutes(){

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        get("/:id/description", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderEventDetails(request, response));
        });

        get("/:id/edit", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderEditEvent(request, response));
        });

        post("/:id/edit", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(EventController.editEvent(request, response));
        });

        get("/:id/remove", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(EventController.removeEvent(request, response));
        });

        get("/event/add", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderAddEvent(request, response));
        });

        post("/event/add", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(EventController.addNew(request, response));
        });

        post("/sort", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.sortEvents(req, res));
        });

        post("/search", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(EventController.searchEngine(request, response));
        });

        get("/admin", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(AdminController.renderAdminInterface(request, response));
        });

        get("/", (Request request, Response response) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderEvents(request, response));
        });

        get("/index", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderEvents(req, res));
        });
    }
}
