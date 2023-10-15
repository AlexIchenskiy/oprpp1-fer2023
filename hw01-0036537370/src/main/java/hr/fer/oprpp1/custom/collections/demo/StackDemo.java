package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Class representing the stack demo usage example.
 */
public class StackDemo {

    /**
     * Main program that performs a postfix expression calculation.
     * @param args Accepts one string argument in a format of a postfix expression
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Only one argument should be provided!");
        }

        String[] tokens = args[0].split("\\s+");

        ObjectStack stack = new ObjectStack();

        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token);
                stack.push(value);
            } catch (NumberFormatException e) {
                int op2 = (int) stack.pop();
                int op1 = (int) stack.pop();

                stack.push(StackDemo.performOperation(op1, op2, token));
            }
        }

        if (stack.size() != 1) {
            System.out.println("Error parsing the input string!");
        } else {
            System.out.println("Expression evaluates to " + stack.pop());
        }
    }

    /**
     * Private method that performs operation op (+, -, *, / or %) on op1 and op2.
     * @param op1 First integer operand
     * @param op2 Second integer operand
     * @param op Operation to be performed
     * @return Integer result of the operation
     */
    private static int performOperation(int op1, int op2, String op) {
        switch (op) {
            case "+":
                return op1 + op2;
            case "-":
                return op1 - op2;
            case "*":
                return op1 * op2;
            case "/":
                if (op2 == 0) {
                    System.out.println("Division by zero");
                    System.exit(0);
                }

                return op1 / op2;
            case "%":
                if (op2 == 0) {
                    System.out.println("Modulo by zero");
                    System.exit(0);
                }
                return op1 % op2;
            default:
                System.out.println("Unsupported operator: " + op);
                System.exit(0);
                return -1;
        }
    }

}
