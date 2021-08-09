package com.todos;

import java.util.Objects;

public final class Todo implements Cloneable {
    public static Todo create(String name) {
        return new Todo(name, 0);
    }

    private final String name;
    private int rank;

    private Todo(String name, int rank) {
        this.name = Guard.againstEmptyString(
            Guard.againstNull(name, "name is null."),
                "name is empty.");
        this.rank = Guard.againstNegative(rank, "rank is negative.");
    }

    @Override
    public Todo clone() {
        try {
            return (Todo)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public void increment() {
        rank++;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Todo)) {
            return false;
        }
        Todo todo = (Todo)obj;
        return rank == todo.rank && name.equals(todo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rank);
    }

    @Override
    public String toString() {
        return "name=" + name + " rank=" + rank;
    }

}
