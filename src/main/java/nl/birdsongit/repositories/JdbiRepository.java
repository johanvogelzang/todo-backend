package nl.birdsongit.repositories;

import nl.birdsongit.model.TodoItem;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JdbiRepository implements Repository {

    Jdbi jdbi;

    public JdbiRepository(String urlConnectString) {
        jdbi = Jdbi.create(urlConnectString);
        jdbi.registerRowMapper(new TodoMapper());
    }

    JdbiRepository(DataSource datasource) {
        this.jdbi = Jdbi.create(datasource);
    }

    @Override
    public void migrate() {
        jdbi.useHandle(handle -> {
            handle.execute("CREATE TABLE todo (id UUID PRIMARY KEY, title VARCHAR, completed BOOLEAN, index INTEGER, url VARCHAR)");
        });
    }

    @Override
    public void save(UUID id, TodoItem todoItem) {
        jdbi.useHandle(handle -> {
            handle.createUpdate("INSERT INTO todo (id, title, completed, index, url) VALUES (:id, :title, :completed, :index, :url)")
                    .bind("id", todoItem.getId())
                    .bind("title", todoItem.getTitle())
                    .bind("completed", todoItem.isCompleted())
                    .bind("index", todoItem.getOrder())
                    .bind("url", todoItem.getUrl())
                    .execute();
        });
    }

    @Override
    public void deleteAll() {
        jdbi.useHandle(handle -> {
            handle.execute("TRUNCATE TABLE todo");
        });
    }

    @Override
    public List<TodoItem> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * from todo")
                .mapTo(TodoItem.class)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<TodoItem> get(UUID uuid) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * from todo WHERE id=:id")
                .bind("id", uuid)
                .mapTo(TodoItem.class)
                .findOne());
    }

    @Override
    public int update(TodoItem todoItem) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE todo SET title = :title, completed = :completed, index = :order WHERE id = :id")
                        .bind("id", todoItem.getId())
                        .bind("title", todoItem.getTitle())
                        .bind("completed", todoItem.isCompleted())
                        .bind("order", todoItem.getOrder())
                        .execute());
    }

    @Override
    public void delete(UUID uuid) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE from todo where id = :id")
                .bind("id", uuid)
                .execute()
        );
    }
}

