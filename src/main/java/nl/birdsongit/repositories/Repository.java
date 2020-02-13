package nl.birdsongit.repositories;

import nl.birdsongit.model.TodoItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository {

    void save(UUID id, TodoItem todoItem);

    void deleteAll();

    List<TodoItem> getAll();

    Optional<TodoItem> get(UUID id);

    int update(TodoItem todoItem);

    void delete(UUID id);

    void migrate();
}
