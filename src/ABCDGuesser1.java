import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * A program that approximates a constant using the de Jager formula with
 * personalized physical constants..
 *
 * @author Omar Takruri
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
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

        // Input the personal numbers w, x, y, and z
        out.print("Enter the personal number w: ");
        double w = getPositiveDoubleNotOne(in, out);
        out.print("Enter the personal number x: ");
        double x = getPositiveDoubleNotOne(in, out);
        out.print("Enter the personal number y: ");
        double y = getPositiveDoubleNotOne(in, out);
        out.print("Enter the personal number z: ");
        double z = getPositiveDoubleNotOne(in, out);

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

        int a = exponents[0];
        while (a <= exponents[exponents.length - 1]) {
            int b = exponents[0];
            while (b <= exponents[exponents.length - 1]) {
                int c = exponents[0];
                while (c <= exponents[exponents.length - 1]) {
                    int d = exponents[0];
                    while (d <= exponents[exponents.length - 1]) {
                        double approximation = Math.pow(w, a) * Math.pow(x, b)
                                * Math.pow(y, c) * Math.pow(z, d);
                        double relativeError = Math
                                .abs((approximation - mu) / mu) * oneHundred;

                        if (relativeError < bestRelativeError) {
                            bestApproximation = approximation;
                            bestRelativeError = relativeError;
                            bestA = a;
                            bestB = b;
                            bestC = c;
                            bestD = d;
                        }

                        d++;
                    }
                    c++;
                }
                b++;
            }
            a++;
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
}
