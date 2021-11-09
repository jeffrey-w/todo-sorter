package com.todos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import com.todos.sorter.Memento;
import com.todos.sorter.Sorter;
import com.todos.todo.Todo;
import com.todos.todo.TodoList;

public class Driver {
    private static Scanner IN = new Scanner(System.in);
    private static Set<String> VALID_INPUT = Set.of("1", "2", "U", "u");
    private static Stack<Memento> HISTORY = new Stack<>();
    private static Sorter SORTER;
    private static Comparator<Todo> RANK_COMPARATOR = (one, two) ->
        Integer.compare(two.getRank(), one.getRank());

    private static final int EX_USAGE = 64;
    private static final int EX_DARAERR = 65;

    public static void main(String[] args) {
        try {
            readTodos(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            error("Usage: java com.todos.Driver <input file>.txt", EX_USAGE);
        }
        while (!SORTER.isSorted()) {
            while (SORTER.hasNext()) {
                if (!SORTER.isCurrentListSingleton()) {
                    Todo current = SORTER.getCurrent();
                    Todo next = SORTER.getNext();
                    chooseBetween(current, next);
                } else {
                    SORTER.advance();
                }
            }
            SORTER = new Sorter(TodoList.merge(SORTER.getLists()));
        }
        int index = 1;
        for (Todo todo : GetSortedTodos()) {
            System.out.println(index++ + ". " + todo.getName());
        }
    }
    
    private static void readTodos(String path) {
        try (Scanner scanner = new Scanner(new FileInputStream(path))) {
            List<Todo> todos = new ArrayList<>();
            while (scanner.hasNextLine()) {
                todos.add(Todo.create(scanner.nextLine()));
            }
            SORTER = new Sorter(todos);

        } catch (FileNotFoundException e) {
            error("FATAL ERROR: cannot read file at " + path + ".", EX_DARAERR);
        }
    }

    private static void chooseBetween(Todo one, Todo two) {
        System.out.print("Would you rather [1] " + one.getName() + " or [2] " + two.getName() + "? ");
        String input = tryGetInput();
        if (input.equalsIgnoreCase("U")) {
            try {
                SORTER.restore(HISTORY.pop());
            } catch (EmptyStackException e) {
                System.out.println("Cannot undo, you are at the beginning of the list.");
            }
        }
        else {
            HISTORY.push(SORTER.save());
            if (input.equals("1")) {
                SORTER.incrementOuter();
            } else {
                SORTER.incrementInner();
            }
            SORTER.advance();
        }
    }

    private static String tryGetInput() {
        String input;
        boolean isNotValid = true;
        do {
            input = IN.next();
            if (VALID_INPUT.contains(input)) {
                isNotValid = false;
            } else {
                System.out.print("Please select either [1] or [2]: ");
            }
        } while (isNotValid);
        return input;
    }

    private static Iterable<Todo> GetSortedTodos() {
        return TodoList.merge(SORTER.getLists()).stream().sorted(RANK_COMPARATOR).collect(Collectors.toList());
    }

    private static void error(String message, int exitCode) {
        System.err.println(message);
        System.exit(exitCode);
    }
}