package com.todos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class TodoTest {
    @Test
    public void TestIncrement() {
        Todo one = Todo.create("TEST");
        Todo two = one.increment();
        assertNotSame(one, two);
        assertEquals(0, one.getRank());
        assertEquals(1, two.getRank());
    }    
}
