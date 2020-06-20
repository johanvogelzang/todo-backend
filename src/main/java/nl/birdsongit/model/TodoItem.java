package nl.birdsongit.model;

import java.util.UUID;

public class TodoItem {

    private UUID id;
    private String title;
    private Boolean completed = Boolean.FALSE;
    private String url;

    private Integer order;

    public TodoItem(UUID uuid) {
        this.id = uuid;
    }

    public TodoItem() {
    }

    public TodoItem patchMe(TodoItem patchItem) {
        setTitle(patchItem.getTitle() != null ? patchItem.getTitle() : getTitle());
        setCompleted(patchItem.isCompleted() != null ? patchItem.isCompleted() : isCompleted());
        setUrl(patchItem.getUrl() != null ? patchItem.getUrl() : getUrl());
        setOrder(patchItem.getOrder() != null ? patchItem.getOrder() : getOrder());
        return this;
    }

    public UUID getId() {
        return id;
    }

    public TodoItem setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TodoItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public TodoItem setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public TodoItem setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public TodoItem setOrder(Integer order) {
        this.order = order;
        return this;
    }
}
