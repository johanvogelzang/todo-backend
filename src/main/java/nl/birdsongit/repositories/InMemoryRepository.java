package nl.birdsongit.repositories;

import nl.birdsongit.model.TodoItem;

import java.util.*;

public class InMemoryRepository implements Repository {

    Map<UUID, TodoItem> todoItems = new HashMap<>();

    @Override
    public void save(UUID id, TodoItem todoItem) {
        this.todoItems.put(id, todoItem);
    }

    @Override
    public void deleteAll() {
        todoItems.clear();
    }

    @Override
    public List<TodoItem> getAll() {
        return new ArrayList<>(todoItems.values());
    }

    @Override
    public TodoItem get(UUID uuid) {
        if (todoItems.containsKey(uuid)) {
            return todoItems.get(uuid);
        }
        return null;
    }

    @Override
    public TodoItem update(TodoItem todo) {
        todoItems.replace(todo.getId(), todo);
        return todoItems.get(todo.getId());
    }

    @Override
    public void delete(UUID uuid) {
        todoItems.remove(uuid);
    }
}
