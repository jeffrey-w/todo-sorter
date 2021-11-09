package com.todos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.todos.todo.Todo;
import com.todos.todo.TodoList;

import org.junit.Before;
import org.junit.Test;

public class TodoListTest {
    private static List<Todo> TODOS = List.of(Todo.create("TEST_ONE"), Todo.create("TEST_TWO"));

    private TodoList list;

    @Before
    public void setup() {
        try {
            Constructor<TodoList> constructor = TodoList.class.getDeclaredConstructor(List.class);
            constructor.setAccessible(true);
            list = constructor.newInstance(TODOS);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStratify() {
        TodoList other = list.incrementAt(0);
        List<TodoList> one = list.sort();
        List<TodoList> two = other.sort();
        assertEquals(1, one.size()); 
        assertEquals(2, two.size());
    }

    @Test
    public void testIncrementAt() {
        TodoList other = list.incrementAt(0);
        assertNotSame(list, other);
    }
}
