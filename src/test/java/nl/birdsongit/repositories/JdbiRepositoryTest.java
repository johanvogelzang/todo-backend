package nl.birdsongit.repositories;

import nl.birdsongit.model.TodoItem;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static nl.birdsongit.config.Environment.DB_URL;
import static org.assertj.core.api.Assertions.assertThat;

class JdbiRepositoryTest {

    JdbiRepository sut = new JdbiRepository(DB_URL);

    @BeforeAll
    public static void init() {
        Flyway.configure()
                .dataSource(DB_URL, null, null)
                .locations("db-migrations").load().migrate();
    }

    @Test
    void when_a_todo_item_is_saved_it_can_be_retrieved() {
        TodoItem todo = new TodoItem(randomUUID());

        sut.save(todo);

        TodoItem storedTodo = sut.retrieve(todo.getId()).get();
        assertThat(storedTodo).isNotNull();
        assertThat(storedTodo.getId()).isEqualTo(todo.getId());
    }
}