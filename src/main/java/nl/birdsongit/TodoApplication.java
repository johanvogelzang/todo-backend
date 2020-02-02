package nl.birdsongit;

import nl.birdsongit.repositories.InMemoryRepository;
import nl.birdsongit.repositories.Repository;
import nl.birdsongit.server.TodoController;
import nl.birdsongit.server.TodoServer;

import static java.lang.String.format;

public class TodoApplication {

    private static final int DEFAULT_PORT = 7000;
    private static final String HOSTNAME = "localhost";
    public static final String ENDPOINT_URL = format("http://%s:%d", HOSTNAME, DEFAULT_PORT);

    public static void main(String[] args) {

        Repository repository = new InMemoryRepository();
        TodoServer todoServer = new TodoServer(new TodoController(ENDPOINT_URL, repository));
        todoServer.start(DEFAULT_PORT);
    }
}