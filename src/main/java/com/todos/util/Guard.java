package com.todos.util;

import java.util.Collection;
import java.util.Objects;

import com.todos.todo.Todo;

public final class Guard {
    public static <T> T againstNull(T object, String message) {
        return Objects.requireNonNull(object, message);
    }

    public static <T extends Collection<Todo>> T againstNullElements(T todos, String message) {
        for (Todo todo : todos) {
            if (todo == null) {
                throw new NullPointerException(message);
            }
        }
        return todos;
    }
    
    public static int againstNegative(int number, String message) {
        if (number < 0) {
            throw new IllegalArgumentException(message);
        }
        return number;
    }
    
    public static String againstEmptyString(String str, String message) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return str;
    }

    private Guard() {
        throw new AssertionError();
    }
}
