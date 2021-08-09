package com.todos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class TodoList implements Cloneable {
    public static List<TodoList> listsFrom(Collection<Todo> todos) {
        TodoList list = new TodoList(List.copyOf(todos));
        return list.stratify();
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

    private List<Todo> todos;

    private TodoList(List<Todo> todos) {
        this.todos = new ArrayList<>(Guard.againstNullElements(
            Guard.againstNull(todos, "todos is null."),
                "todos contains null elements."));
    }

    @Override
    public TodoList clone() {
        try {
            TodoList clone = (TodoList)super.clone();
            clone.todos = clone.todos.stream().map(todo -> todo.clone())
                .toList();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public List<TodoList> stratify() { // TODO rename this
        return todos.stream().collect(Collectors.groupingBy(Todo::getRank, Collectors.toList()))
            .values().stream().map(list -> new TodoList(list)).toList();
    }

    public Todo get(int index) {
        return todos.get(index);
    }
    
    public int size() {
        return todos.size();
    }
}
