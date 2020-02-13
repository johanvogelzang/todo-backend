package nl.birdsongit;

import kong.unirest.Body;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import nl.birdsongit.config.Environment;
import nl.birdsongit.model.TodoItem;
import nl.birdsongit.repositories.JdbiRepository;
import nl.birdsongit.repositories.Repository;
import nl.birdsongit.server.TodoController;
import nl.birdsongit.server.TodoServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class TodoApplicationTest {

    public static final int RANDOM_PORT = Environment.randomPort();
    public static final String ENDPOINT_URL = format("http://localhost:%d", RANDOM_PORT);
    private static TodoServer app;

    @BeforeAll
    public static void beforeAll(){
        Repository repository = new JdbiRepository("jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
        repository.migrate();
        app = new TodoServer(new TodoController(ENDPOINT_URL, repository));
        app.start(RANDOM_PORT);
    }

    @AfterAll
    public static void afterAll(){
        app.stop();
    }

    @Test
    public void GET_to_fetch_info_returns_info() {
        var response = Unirest.get(ENDPOINT_URL + "/info").asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Yes! Up and running.");
    }

    @Test
    public void when_todoitem_is_posted_then_the_item_is_returned_and_has_become_a_id_value() {
        var todoItem = new TodoItem()
            .setTitle("My todoitem")
            .setCompleted(true)
            .setUrl("the-url");

        HttpResponse<TodoItem> response = Unirest
                .post(ENDPOINT_URL)
                .body(todoItem)
                .asObject(TodoItem.class);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody().getTitle()).isEqualTo("My todoitem");
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().isCompleted()).isTrue();
        assertThat(response.getBody().getUrl()).isEqualTo(ENDPOINT_URL + "/" + response.getBody().getId());
    }

    @Test
    public void when_delete_is_called_get_returns_an_json_representation_of_empty_array() {
        Unirest.delete(ENDPOINT_URL);
        Optional<Body> response = Unirest.get(ENDPOINT_URL).getBody();
        assertThat(response).isEqualTo(Optional.empty());
    }

    @Test
    public void when_todoitem_is_posted_then_the_item_can_be_fetched_by_its_url() {
        var todoItem = new TodoItem()
                .setTitle("Fetch me by url")
                .setCompleted(true)
                .setUrl("the-url");

        HttpResponse<TodoItem> postedTodo = Unirest
                .post(ENDPOINT_URL)
                .body(todoItem)
                .asObject(TodoItem.class);

        HttpResponse<TodoItem> response = Unirest
                .get(postedTodo.getBody().getUrl())
                .asObject(TodoItem.class);

        assertThat(response.getBody().getTitle()).isEqualTo("Fetch me by url");
    }

    @Test
    public void when_todoitem_is_patched_then_it_is_partially_updated() {
        var fullTodo = new TodoItem()
                .setTitle("Todo to patch")
                .setOrder(1);
        TodoItem postedTodo = Unirest
                .post(ENDPOINT_URL)
                .body(fullTodo)
                .asObject(TodoItem.class)
                .getBody();

        Unirest.patch(ENDPOINT_URL + "/" + postedTodo.getId())
               .body(new TodoItem().setTitle("I'm a patched TodoItem"))
               .asObject(TodoItem.class);

        var patchedTodo = Unirest
                .get(postedTodo.getUrl())
                .asObject(TodoItem.class)
                .getBody();

        assertThat(patchedTodo.getTitle()).isEqualTo("I'm a patched TodoItem");
        assertThat(patchedTodo.getOrder()).isEqualTo(1);
    }
}