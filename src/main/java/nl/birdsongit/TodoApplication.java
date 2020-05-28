package nl.birdsongit;

import nl.birdsongit.config.Environment;
import nl.birdsongit.repositories.JdbiRepository;
import nl.birdsongit.server.TodoController;
import nl.birdsongit.server.TodoServer;
import org.flywaydb.core.Flyway;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static nl.birdsongit.config.Environment.*;

public class TodoApplication {

    private static final Flyway flyway = Flyway.configure()
            .dataSource(DB_URL, null, null)
            .locations("db-migrations")
            .load();

    public static void main(String[] args) {
        var port = Environment.port();
        var endpointUrl = Environment.hostUrl();
        if (args.length > 0){
            port = ofNullable(args[0]).map(Integer::parseInt).orElse(DEFAULT_PORT);
            endpointUrl = format("%s:%d", DEFAULT_URL, port);
        }
        flyway.migrate();
        new TodoServer(new TodoController(endpointUrl, new JdbiRepository(DB_URL)))
                .start(port);
    }
}