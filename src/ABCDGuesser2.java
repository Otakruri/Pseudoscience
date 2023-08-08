import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * A program that approximates a constant using the de Jager formula with
 * personalized physical constants, using for loops in the main method this
 * time...
 *
 * @author Omar Takruri
 *
 */
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
        // This constructor is intentionally empty
    }

    /**
     * The main method of the program.
     *
     * @param args
     *            the command-line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Input the constant μ`1234567
        out.print("Enter the constant μ to be approximated: ");
        double mu = getPositiveDouble(in, out);

        /*
         * This modification creates two arrays that can be run through using a
         * for loop. Same function as ABCDGueesser1, except, the "w", "x", "y",
         * and "z" are pre-saved in an array, and the corresponding values are
         * stored in an arrray called personalNumbers.
         *
         */
        double[] personalNumbers = new double[4];
        String[] prompts = { "w", "x", "y", "z" };

        for (int i = 0; i < personalNumbers.length; i++) {
            out.print("Enter the personal number " + prompts[i] + ": ");
            personalNumbers[i] = getPositiveDoubleNotOne(in, out);
        }

        // Calculate the exponents and the approximation
        double bestApproximation = 0;
        double bestRelativeError = 1;
        int bestA = 0;
        int bestB = 0;
        int bestC = 0;
        int bestD = 0;
        final int oneHundred = 100;

        final int[] exponents = { -5, -4, -3, -2, -1, -1, -1, -1, 0, 1, 1, 1, 2,
                3, 4, 5 };

        /*
         * iterates through each value of the exponents array, then assigns the
         * lowest relativeError value and the bestApproximation value...
         */

        for (int a : exponents) {
            for (int b : exponents) {
                for (int c : exponents) {
                    for (int d : exponents) {
                        double approximation = calculateApproximation(
                                personalNumbers, a, b, c, d);
                        double relativeError = Math
                                .abs((approximation - mu) / mu) * oneHundred;
                        //Approximation calculation is kept the same, b
                        // because I feel like it would be too much to create
                        // a new method for.

                        if (relativeError < bestRelativeError) {
                            bestApproximation = approximation;
                            bestRelativeError = relativeError;
                            bestA = a;
                            bestB = b;
                            bestC = c;
                            bestD = d;
                        }
                    }
                }
            }
        }

        // Print the results
        out.println("Exponents: a = " + bestA + ", b = " + bestB + ", c = "
                + bestC + ", d = " + bestD);
        out.println("Best approximation: " + bestApproximation);
        out.println("Relative error: "
                + String.format("%.2f%%", bestRelativeError));
        //String.format is used to format the bestRelativeError value to '2'
        //decimal points...

        in.close();
        out.close();
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double number = 0.0;
        boolean validInput = false;

        while (!validInput) {
            String input = in.nextLine();
            boolean isNumber = true;

            //To check if the user's input contains anything that is not a #:
            int i = 0;
            while (i < input.length()) {
                char c = input.charAt(i);
                if (!Character.isDigit(c) && c != '.' && c != '-') {
                    isNumber = false;
                    break;
                }
                i++;
            }

            /*
             * Another test to see if the number is less than 0. keeping in mind
             * the previous while loop (check point).
             */

            if (isNumber) {
                number = Double.parseDouble(input);
                if (number > 0) {
                    validInput = true;
                } else {
                    out.print("Please enter a positive real number: ");
                }
            } else {
                out.print(
                        "Invalid input. Please enter a positive real number: ");
            }
        }

        return number;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double number = 0.0;
        boolean validInput = false;

        while (!validInput) {
            String input = in.nextLine();
            boolean isNumber = true;

            int i = 0;
            while (i < input.length()) {
                char c = input.charAt(i);
                if (!Character.isDigit(c) && c != '.' && c != '-') {
                    isNumber = false;
                    break;
                }
                i++;
            }

            if (isNumber) {
                number = Double.parseDouble(input);
                if (number != 1.0) {
                    validInput = true;
                } else {
                    out.print(
                            "Please enter a positive real number not equal to 1.0: ");
                }
            } else {
                out.print(
                        "Invalid input. Please enter a positive real number not"
                                + " equal to 1.0: ");
            }
        }

        return number;
    }

    /**
     * The new method satisfying the for loops in the main method, and making
     * the program more organized and readable by taking the calculation for the
     * approximation here instead.
     *
     * @param personalNumbers
     *            An array holding the personal numbers entered by the the user
     *
     * @param a
     *            the exponents of the 1st person
     * @param b
     *            the exponents of the 2nd person
     * @param c
     *            the exponents of the 3rd person
     * @param d
     *            the exponents of the 4th person
     * @return the calculated approximation
     */
    private static double calculateApproximation(double[] personalNumbers,
            int a, int b, int c, int d) {
        double w = personalNumbers[0];
        double x = personalNumbers[1];
        double y = personalNumbers[2];
        double z = personalNumbers[3];

        return Math.pow(w, a) * Math.pow(x, b) * Math.pow(y, c)
                * Math.pow(z, d);
    }
}
