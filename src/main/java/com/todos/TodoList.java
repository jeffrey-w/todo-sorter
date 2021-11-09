package com.todos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class TodoList {
    public static List<TodoList> listsFrom(Collection<Todo> todos) {
        TodoList list = new TodoList(List.copyOf(todos));
        return list.sort();
    }

    public static List<Todo> merge(Collection<TodoList> lists) {
        List<Todo> todos = new ArrayList<>();
        for (TodoList list : lists) {
            for (Todo todo : list.todos) {
                todos.add(todo);
            }
        }
        return todos;
    }

    private final List<Todo> todos;

    private TodoList(List<Todo> todos) {
        this.todos = new ArrayList<>(Guard.againstNullElements(
            Guard.againstNull(todos, "todos is null."),
                "todos contains null elements."));
    }

    public List<TodoList> sort() {
        return todos.stream().collect(Collectors.groupingBy(Todo::getRank, Collectors.toList()))
            .values().stream().map(list -> new TodoList(list)).toList();
    }

    public TodoList incrementAt(int index) {
        List<Todo> todos = new ArrayList<>(this.todos);
        todos.set(index, todos.get(index).increment());
        return new TodoList(todos);
    }

    public Todo get(int index) {
        return todos.get(index);
    }
    
    public int size() {
        return todos.size();
    }
}
