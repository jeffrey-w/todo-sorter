package com.todos;

import java.util.Collection;
import java.util.List;

public final class Sorter {
    private List<TodoList> lists;
    private int cursor;
    private int outer;
    private int inner;

    public Sorter(Collection<Todo> todos) {
        lists = TodoList.listsFrom(todos);
        inner = 1;
    }

    public Todo getCurrent() {
        return lists.get(cursor).get(outer);
    }

    public Todo getNext() {
        return lists.get(cursor).get(inner);
    }

    public List<TodoList> getLists() {
        return lists;
    }

    public void advance() {
        inner++;
        if (inner == lists.get(cursor).size()) {
            outer++;
            inner = outer + 1;
            if (outer == lists.get(cursor).size() - 1) {
                cursor++;
                outer = 0;
                inner = 1;
            }
        }
    }

    public boolean hasNext() {
        return cursor < lists.size();
    }

    public boolean isSorted() {
        return lists.stream().allMatch(list -> list.size() == 1);
    }

    public Memento save() {
        return new Memento(lists, cursor, outer, inner);
    }

    public void restore(Memento memento) {
        lists = memento.getLists();
        cursor = memento.getCursor();
        outer = memento.getOuter();
        inner = memento.getInner();
    }
}
