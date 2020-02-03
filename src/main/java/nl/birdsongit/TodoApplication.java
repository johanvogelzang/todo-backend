package nl.birdsongit;

import nl.birdsongit.config.Environment;
import nl.birdsongit.repositories.InMemoryRepository;
import nl.birdsongit.server.TodoController;
import nl.birdsongit.server.TodoServer;

public class TodoApplication {

    public static final String ENDPOINT_URL = Environment.hostUrl();

    public static void main(String[] args) {
        var repository = new InMemoryRepository();
        var todoServer = new TodoServer(new TodoController(ENDPOINT_URL, repository));
        todoServer.start(Environment.port());
    }
}