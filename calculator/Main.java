package calculator;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class Main {
    static final Pattern validateInput = Pattern.compile("^[\\d-]?[\\d\\h+-]*[\\d]$");
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

            if (input.matches(validateInput.pattern())) {
                Processor calc = new Processor();
                System.out.println(calc.processInput(input));
            } else {
                System.out.println("Invalid expression");
            }
        }
    }

    private static boolean parseCommand(String command) {
        switch (command.toLowerCase()) {
            case "/exit":
                return true;
            case "/help":
                System.out.println("Calculate the total of integers (whole numbers) input on a single line separated by the");
                System.out.println("operators + or - with a space between the integers and operators. i.e. -2 + 4, not -2+4.");
                System.out.println("Multiple consecutive same operators will be treated as addition if +'s or even amount of");
                System.out.println("-'s, as subtraction if odd amount of -'s.");
                break;
            default:
                System.out.println("Unknown command:");
                break;
        }

        return false;
    }
}
