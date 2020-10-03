# Smart Calculator
A Java project from JetBrains Academy ([hyperskill.org](https://hyperskill.org))

## About
Calculators are a very helpful tool that we all use on a regular basis. Why not create one yourself, and make it really
special? In this project, you will write a calculator that not only adds, subtracts, and multiplies, but is also smart
enough to remember your previous calculations.

#### Stage 1: 2+2
Write a program that reads two integer numbers from the same line and prints their sum in the standard output. Numbers
can be positive, negative or zero.

#### Stage 2: 2+2+
Write a program that reads two numbers in a loop and prints the sum in the standard output. If a user enters only a
single number, the program should print the same number. If a user enters an empty lne, the program should ignore it.

When the command `/exit` is entered, the program must print `Bye!`, and then stop.

#### Stage 3: Count them all
At this stage, the program should read an unlimited sequence of numbers from the standard input and calculate their sum.
Also, add a `/help` command to print some information about the program. If you encounter an empty line, do not output
anything.

#### Stage 4: Add subtractions
At this stage, the program should support the addition `+` and subtraction `-` operators.

The program must calculate expressions like these: `4 + 6 - 8`, `2 - 3 - 4`, and so on. It must support both unary and
binary minus operators. If the user has entered several same operators following each other, the program still should
work (like Java or Python REPL).

Consider that the even number of minuses gives a plus, and the odd number of minuses gives a minus! Look at it this way:
 `2 -- 2` equals `2 - (-2)` equals `2 + 2`.

Modify the result of the `/help` command to explain these operations. The program should not quit until the `/exit`
command is entered.

#### Stage 5: Error!
Modify the program to handle different cases when the given expression has an invalid format. The program should print
`Invalid expression` in such cases. The program must never throw the `NumberFormatException` or any other exception.
Also, print `Unknown command` if a user enters an invalid command.

#### Stage 6: Variables
At this stage, your program should support variables. We suppose that the name of a variable (identifier) can contain
only Latin letters. The case is also important; for example, `n` is not the same as `N`. The value can be an integer
number or a value of another variable. Use `Map` to support variables.

#### Stage 7: I've got the power
At this stage, your program should add support for multiplication `*`, integer division `/` and parentheses `(...)`. They
have a higher priority than addition `+` and subtraction `-`. Do not forget about variables; they, and the unary minus
operator, should still work. Modify the result of the `/help` command to explain all possible operators. As a bonus,
you may add the power operator `^` that has higher priority than `*` and `/`.

#### Stage 8: Very big
At this stage, your program must support arithmetic operations (`+`, `-`, `*`, `/`) with very large numbers as well as
parentheses to change the priority within an expression.

There are two ways to solve it. As an easy way, you may use the standard class for working with large numbers, just
correctly apply it to your solution. If you want to practice algorithms, you may develop your own class for large
numbers and implement algorithms for the listed arithmetic operations.

The program should not stop until the user enters the `/exit` command.