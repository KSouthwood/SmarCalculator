package calculator;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class Main {
    static final Pattern digits = Pattern.compile("^-?(0|[1-9]\\d*)(?<!-0)$");
    static final Stack<Integer> stack = new Stack<>();

    public static void main(String[] args) {
        readInput();
        System.out.println("Bye!");
    }

    private static void readInput() {
        boolean keepReading = true;
        Scanner scanner = new Scanner(System.in);
        while (keepReading) {
            stack.clear();
            String input = scanner.nextLine();

            if (input.isBlank()) {
                continue;
            }

            if (input.charAt(0) == '/') {   // checks to see if a command was entered (all commands begin with a forward slash)
                if (parseCommand(input)) {  // process the command
                    keepReading = false;    // return from the method if /exit was the command (only way the return value would be true)
                }
                continue;
            }

            for (var number : input.split("\\s")) {
                if (number.matches(digits.pattern())) {
                    stack.push(Integer.parseInt(number));
                } else {
                    System.out.printf("Invalid input: %s%n", number);
                    break;
                }
            }

            sumNumbers();
        }
    }

    private static boolean parseCommand(String command) {
        switch (command.toLowerCase()) {
            case "/exit":
                return true;
            case "/help":
                System.out.println("Calculate the sum of integers (whole numbers) input on a single line separated by spaces.");
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }

        return false;
    }

    private static void sumNumbers() {
        int sum = 0;
        while (!stack.empty()) {
            sum += stack.pop();
        }
        System.out.println(sum);
    }
}
