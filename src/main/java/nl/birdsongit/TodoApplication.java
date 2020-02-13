package nl.birdsongit;

import nl.birdsongit.config.Environment;
import nl.birdsongit.repositories.JdbiRepository;
import nl.birdsongit.server.TodoController;
import nl.birdsongit.server.TodoServer;

public class TodoApplication {

    public static final String ENDPOINT_URL = Environment.hostUrl();

    public static void main(String[] args) {
//        var repository = new HashMapRepository();
        var repository = new JdbiRepository("jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
        repository.migrate();
        var todoServer = new TodoServer(new TodoController(ENDPOINT_URL, repository));
        todoServer.start(Environment.port());
    }
}