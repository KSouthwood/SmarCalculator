package calculator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Processor {
    static final Pattern validInteger = Pattern.compile("^-?(0|[1-9]\\d*)(?<!-0)$");
    static final Pattern validVariableName = Pattern.compile("[a-zA-Z]+");
    static final Pattern variableAssign = Pattern.compile("[a-zA-Z]+[\\s]*=[\\s]*((-?(0|[1-9]\\d*)(?<!-0))|[a-zA-Z]+)$");
    static final Pattern validAddition = Pattern.compile("^\\++$");
    static final Pattern validSubtraction = Pattern.compile("^-+$");
    
    static ArrayDeque<String> inputQueue;

    private long result;
    private final HashMap<String, Long> variableMap = new HashMap<>();

    public void processInput(String input) {
        String[] inputArray = input.split("\\s+");

        // process and simplify input, handling input errors along the way
        StringBuilder newExpression = new StringBuilder();
        for (var piece : inputArray) {
            if (validInteger.matcher(piece).matches()) {
                newExpression.append(piece).append(" ");
                continue;
            }

            if (validVariableName.matcher(piece).matches()) {
                if (variableMap.containsKey(piece)) {
                    newExpression.append(piece).append(" ");
                    continue;
                } else {
                    System.out.println("Unknown variable");
                    return;
                }
            }

            if (validAddition.matcher(piece).matches()) {
                newExpression.append("+ ");
                continue;
            }

            if (validSubtraction.matcher(piece).matches()) {
                newExpression.append(piece.length() % 2 == 0 ? "+ " : "- ");
                continue;
            }

            System.out.println("Invalid token: " + piece);
            return;
        }

        calculateResult(newExpression.toString());
    }

    private void calculateResult(String expression) {
        inputQueue = new ArrayDeque<>(Arrays.asList(expression.split("\\s")));
        result = 0;

        while (!inputQueue.isEmpty()) {
            String part = inputQueue.pollFirst();

            if (validInteger.matcher(part).matches()) { // should only ever execute with a number as the first item on the line
                result = Long.parseLong(part);
            }

            if (validVariableName.matcher(part).matches()) {
                if (variableMap.containsKey(part)) {
                    result = variableMap.get(part);
                } else {
                    System.out.println("Unknown variable");
                    return;
                }
            }

            if (part.equals("+")) {
                add(inputQueue.pollFirst());
            }

            if (part.equals("-")) {
                subtract(inputQueue.pollFirst());
            }
        }

        System.out.println(result);
    }

    private void add(String addend) {
        if (addend.matches(validInteger.pattern())) {
            result += Long.parseLong(addend);
        } else if (validVariableName.matcher(addend).matches()) {
            if (variableMap.containsKey(addend)) {
                result += variableMap.get(addend);
            } else {
                System.out.println("Unknown variable");
            }
        } else {
            System.out.println("Invalid input: " + addend);
        }
    }

    private void subtract(String subtrahend) {
        if (subtrahend.matches(validInteger.pattern())) {
            result -= Long.parseLong(subtrahend);
        } else if (validVariableName.matcher(subtrahend).matches()) {
            if (variableMap.containsKey(subtrahend)) {
                result -= variableMap.get(subtrahend);
            } else {
                System.out.println("Unknown variable");
            }
        } else {
            System.out.println("Invalid input: " + subtrahend);
        }
    }

    /**
     * Takes an input String that contains an equals sign ('=') and parses it to assign a value to a variable, checking
     * the validity of names, values and existing values
     *
     * @param input String to be parsed
     */
    public void assignToVariable(String input) {
        inputQueue = new ArrayDeque<>(Arrays.asList(input.split("\\s*=\\s*")));
        if (inputQueue.size() != 2) {
            System.out.println("Invalid assignment");
            return;
        }

        String assignor = inputQueue.pollFirst();
        String assignee = inputQueue.pollFirst();

        if (!validVariableName.matcher(assignor).matches()) {
            // The first item isn't a valid variable name
            System.out.println("Invalid identifier");
            return;
        }

        if (validInteger.matcher(assignee).matches()) {
            variableMap.put(assignor, Long.parseLong(assignee)); // assign the integer to the variable
        } else if (validVariableName.matcher(assignee).matches()) { // check if we have a variable name
            if (variableMap.containsKey(assignee)) {
                // variable exists so assign its value to variableName
                variableMap.put(assignor, variableMap.get(assignee));
            } else {
                // variable doesn't exist
                System.out.println("Unknown variable");
            }
        } else {
            // part after equals sign wasn't a variable name or integer, so we can't assign anything
            System.out.println("Invalid assignment");
        }
    }
}
