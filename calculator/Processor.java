package calculator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Processor {
    final Pattern validateInteger = Pattern.compile("^-?(0|[1-9]\\d*)(?<!-0)$");
    private long result;

    public long processInput(String input) {
        ArrayDeque<String> inputQueue = new ArrayDeque<>(Arrays.asList(input.split("\\s+")));

        result = Long.parseLong(inputQueue.pop());

        while (!inputQueue.isEmpty()) {
            String operator = inputQueue.pop();
            switch (operator.charAt(0)) {
                case '+':
                    add(inputQueue.pop());
                    break;
                case '-':
                    if (operator.length() % 2 == 0) {
                        add(inputQueue.pop());
                    } else {
                        subtract(inputQueue.pop());
                    }
                    break;
                default:
                    System.out.println("Invalid input: " + operator);
                    break;
            }
        }

        return result;
    }

    private void add(String addend) {
        if (addend.matches(validateInteger.pattern())) {
            result += Long.parseLong(addend);
        } else {
            System.out.println("Invalid input: " + addend);
        }
    }

    private void subtract(String subtrahend) {
        if (subtrahend.matches(validateInteger.pattern())) {
            result -= Long.parseLong(subtrahend);
        } else {
            System.out.println("Invalid input: " + subtrahend);
        }
    }
}
