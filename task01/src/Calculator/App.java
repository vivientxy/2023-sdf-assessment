package Calculator;

import java.io.Console;
import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome.");

        // import console
        Console cons = System.console();
        String input = "";
        Calculator calc = new Calculator();

        while (!input.equals("exit")) {
            // wait for user prompt
            input = cons.readLine("> ");
            if (calc.isValidStringInput(input)) {
                BigDecimal result = calc.calculate(input);
                System.out.println(result);
            }
        }

        System.out.println("Bye bye");
    }
}