class myScanner {

    private static java.util.Scanner input = new java.util.Scanner(System.in);

    static String readLine(String string){
        System.out.println(string);
        String x = input.nextLine();
        while (x.trim().isEmpty()) {
            System.out.println(string);
            x=input.nextLine();
        }
        return x;
    }

    static double readDouble(String string){

        boolean result;
        double x =0;
        do {
            System.out.println(string);
            String y = input.nextLine();
            while (y.trim().isEmpty()) {
                System.out.println(string);
                y=input.nextLine();
            }
            try {
                x = Double.parseDouble(y);
                return x;
            }catch (java.lang.NumberFormatException e){
                result =false;
            }
        }while (!result);
        return x;
    }

    static int readInt(String string){
        boolean result;
        int x =0;
        do {
            System.out.println(string);
            String y = input.nextLine();
            while (y.trim().isEmpty()) {
                System.out.println(string);
                y=input.nextLine();
            }
            try {
                x = Integer.parseInt(y);
                return x;
            }catch (java.lang.NumberFormatException e){
                result =false;
            }
        }while (!result);
        return x;
    }

    static int readInt(){
        boolean result;
        int x =0;
        do {
        String y = input.nextLine();
        while (y.trim().isEmpty()) {
            System.out.println("Please enter an integer!");
            y=input.nextLine();
        }
            try {
                 x = Integer.parseInt(y);
                return x;
            }catch (java.lang.NumberFormatException e){
                System.out.println("Please enter an integer!");
                result =false;
            }
        }while (!result);
        return x;
    }

}