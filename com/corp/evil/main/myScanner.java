class myScanner {

    private static java.util.Scanner input = new java.util.Scanner(System.in);

    private static final int DEFAULT_TRIES = 3;

    /**
     * Function to read a line of user input from the command line.
     * The function is supposed to retrieve a non-empty String (after removing leading and trailing spaces)
     *
     * @param prompt system output asking for the user input
     * @param tries  number of attempts to read a allowed String before returning NULL
     * @return return the line entered by the user or NULL if the user failed to produce accepted input.
     */
    static String readLine(String prompt, int tries) {
        int cnt = 0;
        String x;
        do {
            if (cnt == tries - 1) {
                Print.println("Last try. Please enter a String containing at least one not empty character.");
            } else if (cnt > 0) {
                Print.println("Your string was either empty or only contained spaces. Please try again.");
            }
            Print.println(prompt);
            x = input.nextLine();
        } while (x.trim().isEmpty() && ++cnt < tries);
        if (cnt == tries) {
            return null;
        }
        return x;
    }


    /**
     * Function to read a line of user input from the command line.
     * Using DEFAULT_TRIES because the number of tries isn't specified with this function-call
     *
     * @param prompt system output asking for the userinput
     * @return the result of readLine(prompt, DEFAULT_TRIES)
     */
    static String readLine(String prompt) {
        return readLine(prompt, DEFAULT_TRIES);
    }

    static double readDouble(String string) {

        boolean result;
        double x = 0;
        do {
            Print.println(string);
            String y = input.nextLine();
            while (y.trim().isEmpty()) {
                Print.println(string);
                y = input.nextLine();
            }
            try {
                x = Double.parseDouble(y);
                return x;
            } catch (java.lang.NumberFormatException e) {
                result = false;
            }
        } while (!result);
        return x;
    }

    static int readInt(String string) {
        boolean result;
        int x = 0;
        do {
            Print.println(string);
            String y = input.nextLine();
            while (y.trim().isEmpty()) {
                Print.println(string);
                y = input.nextLine();
            }
            try {
                x = Integer.parseInt(y);
                return x;
            } catch (java.lang.NumberFormatException e) {
                result = false;
            }
        } while (!result);
        return x;
    }

    static int readInt() {
        boolean result;
        int x = 0;
        do {
            String y = input.nextLine();
            while (y.trim().isEmpty()) {
                Print.println("Please enter an integer!");
                y = input.nextLine();
            }
            try {
                x = Integer.parseInt(y);
                return x;
            } catch (java.lang.NumberFormatException e) {
                Print.println("Please enter an integer!");
                result = false;
            }
        } while (!result);
        return x;
    }

}