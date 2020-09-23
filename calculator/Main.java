package calculator;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class Main {
    static final Pattern digits = Pattern.compile("^-?(0|[1-9]\\d*)(?<!-0)$");
    static final Stack<Integer> stack = new Stack<>();

    public static void main(String[] args) {
        readInput();
        int sum = 0;
        while (!stack.empty()) {
            sum += stack.pop();
        }
        System.out.println(sum);
    }

    private static void readInput() {
        boolean inputError = true;
        Scanner scanner = new Scanner(System.in);
        while (inputError) {
            inputError = false;
            stack.clear();
            String[] input = scanner.nextLine().split("\\s");
            for (var number : input) {
                if (number.matches(digits.pattern())) {
                    stack.push(Integer.parseInt(number));
                } else {
                    inputError = true;
                    System.out.printf("Invalid input: %s%n", number);
                    break;
                }
            }
        }
    }
}
