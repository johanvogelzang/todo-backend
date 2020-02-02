package nl.birdsongit.server;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.core.util.Header;
import nl.birdsongit.model.TodoItem;

public class TodoServer extends Javalin {

    private TodoController todoController;
    private Javalin javalinServer;

    public TodoServer(TodoController todoController) {
        this.todoController = todoController;
        this.javalinServer = createServer();
    }

    public TodoServer start(int port) {
        this.javalinServer.start(port);
        return this;
    }

    private Javalin createServer() {

        var app = create(JavalinConfig::enableCorsForAllOrigins);

        allowCorsHeadersAndMethods(app);

        app.get("/info", ctx -> ctx.result("Yes! Up and running."));
        app.get("/:id", ctx -> ctx.json(todoController.get(ctx.pathParam("id"))));
        app.get("/", ctx -> ctx.json(todoController.getAll()));
        app.post("/", ctx -> ctx.json(todoController.add(ctx.bodyAsClass(TodoItem.class))));
        app.delete("/", ctx -> todoController.deleteAll());
        app.delete("/:id", ctx -> todoController.delete(ctx.pathParam("id")));
        app.patch("/:id", ctx -> ctx.json(todoController.patch(ctx.pathParam("id"), ctx.bodyAsClass(TodoItem.class))));

        return app;
    }

    private void allowCorsHeadersAndMethods(Javalin app) {
        app.before("*", ctx -> {
            if (ctx.header(Header.ACCESS_CONTROL_REQUEST_HEADERS) != null) {
                ctx.header(Header.ACCESS_CONTROL_ALLOW_HEADERS);
            }
            if (ctx.header(Header.ACCESS_CONTROL_REQUEST_METHOD) != null) {
                ctx.header(Header.ACCESS_CONTROL_ALLOW_METHODS);
            }
        });
    }
}
