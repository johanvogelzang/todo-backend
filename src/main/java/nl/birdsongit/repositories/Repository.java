package nl.birdsongit.repositories;

import nl.birdsongit.model.TodoItem;

import java.util.List;
import java.util.UUID;

public interface Repository {

    void save(UUID id, TodoItem todoItem);

    void deleteAll();

    List<TodoItem> getAll();

    TodoItem get(UUID id);

    TodoItem update(TodoItem patchMe);

    void delete(UUID fromString);
}
