package nl.birdsongit.server;

import io.javalin.http.NotFoundResponse;
import nl.birdsongit.model.TodoItem;
import nl.birdsongit.repositories.Repository;

import java.util.*;

public class TodoController {

    Repository repository;
    private String endpointUrl;

    public TodoController(String endpointUrl, Repository repository) {
        this.endpointUrl = endpointUrl;
        this.repository = repository;
    }

    public TodoItem add(TodoItem todoItem) {
        var id = UUID.randomUUID();
        todoItem.setId(id)
                .setUrl(endpointUrl + "/" + id);
        repository.save(id, todoItem);
        return todoItem;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<TodoItem> getAll() {
        return repository.getAll();
    }

    public TodoItem get(String id) {
        TodoItem todo = repository.get(UUID.fromString(id));
        if (todo != null) return todo;
        throw new NotFoundResponse();
    }

    public TodoItem patch(String id, TodoItem patchItem) {
        TodoItem todoToPatch = repository.get(UUID.fromString(id));
        return repository.update(todoToPatch.patchMe(patchItem));
    }

    public void delete(String id) {
        repository.delete(UUID.fromString(id));
    }
}