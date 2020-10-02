package calculator;

import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {
    private static final Pattern VALID_INTEGER = Pattern.compile("-?(0|[1-9]\\d*)(?<!-0)");
    private static final Pattern VARIABLE_NAME = Pattern.compile("([a-zA-Z]+)");
    private static final Pattern ADDITION = Pattern.compile("[+]+");
    private static final Pattern SUBTRACTION = Pattern.compile("[-]+");
    private static final Pattern MULTIPLICATION = Pattern.compile("[*]+");
    private static final Pattern DIVISION = Pattern.compile("/+");
    private static final Pattern EXPONENT = Pattern.compile("[\\^]+");
    private static final Pattern PARENTHESES = Pattern.compile("[()]");

    private static final Pattern INPUT_PATTERN = Pattern.compile(VARIABLE_NAME +
            "|(" + ADDITION + "|" + SUBTRACTION + "|" + MULTIPLICATION + "|" +
            DIVISION + "|" + EXPONENT + "|" + PARENTHESES + ")|" + VALID_INTEGER);

    private static final Stack<String> operatorStack = new Stack<>();
    private final HashMap<String, Long> variableMap = new HashMap<>();
    private static final StringBuilder postfixExpression = new StringBuilder();

    public void processInput(String input) {
        postfixExpression.setLength(0);
        operatorStack.clear();
        Matcher matcher = INPUT_PATTERN.matcher(input);

        while (matcher.find()) {
            // integer match
            if (matcher.group(3) != null) {
                postfixExpression.append(matcher.group()).append(" ");
                continue;
            }

            // variable name match
            if (matcher.group(1) != null) {
                if (variableMap.containsKey(matcher.group())) {
                    postfixExpression.append(variableMap.get(matcher.group())).append(" ");
                    continue;
                } else {
                    System.out.println("Unknown variable: " + matcher.group());
                    return;
                }
            }

            // operator match
            if (matcher.group(2) != null) {
                switch (matcher.group().charAt(0)) {
                    case '(':
                        operatorStack.push("(");
                        break;
                    case ')':
                        boolean leftFound = false;
                        while (!operatorStack.isEmpty()) {
                            if (!operatorStack.peek().equals("(")) {
                                postfixExpression.append(operatorStack.pop()).append(" ");
                            } else {
                                operatorStack.pop();
                                leftFound = true;
                                break;
                            }
                        }
                        if (!leftFound) {
                            System.out.println("Invalid expression");
                            return;
                        }
                        break;
                    case '+':
                        pushOperator("+", "");
                        break;
                    case '-':
                        pushOperator(matcher.group().length() % 2 == 0 ? "+" : "-", "");
                        break;
                    case '*':
                        if (matcher.group().length() > 1) {
                            System.out.println("Invalid expression");
                            return;
                        }
                        pushOperator("*", "[-+]");
                        break;
                    case '/':
                        if (matcher.group().length() > 1) {
                            System.out.println("Invalid expression");
                            return;
                        }
                        pushOperator("/", "[-+]");
                        break;
                    case '^':
                        if (matcher.group().length() > 1) {
                            System.out.println("Invalid expression");
                            return;
                        }
                        pushOperator("^", "[-+*/]");
                        break;
                    default:
                        break;
                }
                continue;
            }

            System.out.println("Invalid operand: " + matcher.group());
            return;
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().equals("(")) {
                System.out.println("Invalid expression");
                return;
            }
            postfixExpression.append(operatorStack.pop()).append(" ");
        }

//        System.out.println(postfixExpression.toString().trim());
        calculateResult(postfixExpression.toString().trim());
    }

    private void pushOperator(String operator, String lowerPrecedence) {
        if (operatorStack.isEmpty() || operatorStack.peek().contains("(")) {
            operatorStack.push(operator);
        } else if (operatorStack.peek().matches(lowerPrecedence)) {
            operatorStack.push(operator);
        } else {
            while (!operatorStack.isEmpty() && !operatorStack.peek().matches("\\(") && !operatorStack.peek().matches(lowerPrecedence)) {
                postfixExpression.append(operatorStack.pop()).append(" ");
            }
            operatorStack.push(operator);
        }
    }

    private void calculateResult(String expression) {
        Stack<Long> resultStack = new Stack<>();

        for (var operand : expression.split("\\s")) {
            if (operand.matches(VALID_INTEGER.pattern())) {
                resultStack.push(Long.parseLong(operand));
                continue;
            }

            switch (operand) {
                case "+":
                    resultStack.push(resultStack.pop() + resultStack.pop());
                    break;
                case "-":
                    long subtrahend = resultStack.pop();
                    resultStack.push(resultStack.pop() - subtrahend);
                    break;
                case "*":
                    resultStack.push(resultStack.pop() * resultStack.pop());
                    break;
                case "/":
                    long divisor = resultStack.pop();
                    resultStack.push(resultStack.pop() / divisor);
                    break;
                case "^":
                    double exponent = (double) resultStack.pop();
                    double base = (double) resultStack.pop();
                    resultStack.push((long) Math.pow(base, exponent));
                    break;
                default:
                    System.out.println("Error! Invalid operator: " + operand);
                    return;
            }
        }

        System.out.println(resultStack.pop());
        if (!resultStack.isEmpty()) {
            System.out.println("Calculation error! Numbers still in stack!");
        }
    }

    /**
     * Takes an input String that contains an equals sign ('=') and parses it to assign a value to a variable, checking
     * the validity of names, values and existing values
     *
     * @param input String to be parsed
     */
    public void assignToVariable(String input) {
        String[] operands = input.split("\\s*=\\s*");
        if (operands.length != 2) {
            System.out.println("Invalid assignment");
            return;
        }

        String assignor = operands[0];
        String assignee = operands[1];

        if (!VARIABLE_NAME.matcher(assignor).matches()) {
            // The first item isn't a valid variable name
            System.out.println("Invalid identifier");
            return;
        }

        if (VALID_INTEGER.matcher(assignee).matches()) {
            variableMap.put(assignor, Long.parseLong(assignee)); // assign the integer to the variable
        } else if (VARIABLE_NAME.matcher(assignee).matches()) { // check if we have a variable name
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
