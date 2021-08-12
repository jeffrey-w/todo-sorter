package com.todos;

import java.util.ArrayList;
import java.util.List;

public final class Memento {
    private final List<TodoList> lists;
    private final int cursor;
    private final int outer;
    private final int inner;

    public Memento(List<TodoList> lists, int cursor, int outer, int inner) {
        this.lists = new ArrayList<>(lists);
        this.cursor = cursor;
        this.outer = outer;
        this.inner = inner;
    }

    public List<TodoList> getLists() {
        return lists;
    }

    public int getCursor() {
        return cursor;
    }

    public int getOuter() {
        return outer;
    }

    public int getInner() {
        return inner;
    }
}
