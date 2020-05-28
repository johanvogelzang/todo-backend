package nl.birdsongit.repositories;

import nl.birdsongit.model.TodoItem;

import java.util.*;

public class HashMapRepository implements Repository {

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
    public Optional<TodoItem> retrieve(UUID id) {
        return Optional.ofNullable(todoItems.get(id));
    }

    @Override
    public int update(TodoItem todoItem) {
        todoItems.replace(todoItem.getId(), todoItem);
        return 1;
    }

    @Override
    public void delete(UUID id) {
        todoItems.remove(id);
    }
}
