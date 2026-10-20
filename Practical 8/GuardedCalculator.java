import java.util.Scanner;
import java.util.InputMismatchException;

class DivideByZeroException extends ArithmeticException {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {

    public static double calculate(double a, double b, char op)
            throws DivideByZeroException {

        switch (op) {
            case '+':
                return a + b;

            case '-':
                return a - b;

            case '*':
                return a * b;

            case '/':
                if (b == 0) {
                    throw new DivideByZeroException("Cannot divide by zero!");
                }
                return a / b;

            default:
                throw new IllegalArgumentException("Invalid operator!");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        int attempt = 0;

        while (!valid) {

            attempt++;

            try {
                System.out.println("\nCalculator Attempt " + attempt);

                System.out.print("Enter first number: ");
                double num1 = sc.nextDouble();

                System.out.print("Enter operator (+, -, *, /): ");
                char op = sc.next().charAt(0);

                System.out.print("Enter second number: ");
                double num2 = sc.nextDouble();

                double result = calculate(num1, num2, op);

                System.out.println("Result = " + result);

                valid = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid number! Please enter numbers only.");
                sc.nextLine();
            } catch (DivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("Attempt " + attempt + " has been logged.");
            }
        }

        System.out.println("\nValid calculation completed!");

        sc.close();
    }
}
