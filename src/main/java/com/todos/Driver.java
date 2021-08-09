package com.todos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Driver {
    private static Reader IN = System.console().reader();
    private static Set<Character> VALID_INPUT = Set.of('1', '2', 'U', 'u');
    private static Stack<Memento> HISTORY = new Stack<>();
    private static Sorter SORTER;

    private static final int EX_USAGE = 64;
    private static final int EX_DARAERR = 65;
    private static final int EX_IOERR = 74;

    public static void main(String[] args) {
        try {
            readTodos(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            error("Usage: java com.todos.Driver <input file>.txt", EX_USAGE);
        }
        while (!SORTER.isSorted()) {
            while (SORTER.hasNext()) {
                Todo current = SORTER.getCurrent();
                Todo next = SORTER.getNext();
                chooseBetween(current, next);
            }
            SORTER = new Sorter(TodoList.merge(SORTER.getLists()));
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
        char input = tryGetInput();
        if (input == 'U' || input == 'u') {
            SORTER.restore(HISTORY.pop());
        }
        else {
            if (input == '1') {
                one.increment();
            } else {
                two.increment();
            }
            HISTORY.push(SORTER.save());
            SORTER.advance();
        }
    }

    private static char tryGetInput() {
        char input = 0;
        boolean isNotValid = true;
        do {
            try {
                input = (char)IN.read();
                IN.read();
            } catch (IOException e) {
                error("FATAL ERROR: could not read input.", EX_IOERR);
            }
            if (!VALID_INPUT.contains(input)) {
                System.out.print("Please select either [1] or [2]: ");
            } else {
                isNotValid = false;
            }
        } while (isNotValid);
        return input;
    }

    private static void error(String message, int exitCode) {
        System.err.println(message);
        System.exit(exitCode);
    }
}
 