package com.todos;

import java.util.Objects;

/**
 * The {@code Todo} class specifies properties and operations on a single item of a {@code TodoList}.
 * 
 * @author Jeff Wilgus
 * @version 1.0
 * @see TodoList
 */
public final class Todo {

    /**
     * Creates a new {@code Todo} with the specified {@code name}.
     * 
     * @param name the identifier for the new {@code Todo}
     * @return a new {@code Todo}
     * @throws IllegalArgumentException if the specified {@code name} is the empty string
     * @throws NullPointerException if the specified {@code name} is {@code null}
     */
    public static Todo create(String name) {
        return new Todo(name, 0);
    }

    private final String name;
    private final int rank;

    private Todo(String name, int rank) {
        this.name = Guard.againstEmptyString(
            Guard.againstNull(name, "name is null."),
                "name is empty.");
        this.rank = Guard.againstNegative(rank, "rank is negative.");
    }

    /**
     * Provides the identifier of this {@code Todo}.
     * 
     * @return this {@code Todo}'s name
     */
    public String getName() {
        return name;
    }

    /**
     * Provides the priority of this {@code Todo}.
     * 
     * @return this {@code Todo}'s rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Provides a new {@code Todo} with a rank that is one greater than this {@code Todo}.
     * 
     * @return a new {@code Todo}
     */
    public Todo increment() {
        return new Todo(name, rank + 1);
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
