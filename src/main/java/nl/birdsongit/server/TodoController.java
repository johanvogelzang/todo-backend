package nl.birdsongit.server;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import nl.birdsongit.model.TodoItem;
import nl.birdsongit.repositories.Repository;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

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
        try {
            return repository.retrieve(UUID.fromString(id))
                    .orElseThrow(NotFoundResponse::new);
        } catch (IllegalArgumentException iae) {
            throw new BadRequestResponse("Not an UUID!") ;
        }
    }

    public TodoItem patch(String id, TodoItem patchItem) {
        TodoItem todoToPatch = this.get(id);
        if (repository.update(todoToPatch.patchMe(patchItem)) == 1) {
            return this.get(id);
        }
        throw new IllegalStateException(format("Patching of todoItem [%s] not successful", todoToPatch.getId()));
    }

    public void delete(String id) {
        repository.delete(UUID.fromString(id));
    }
}