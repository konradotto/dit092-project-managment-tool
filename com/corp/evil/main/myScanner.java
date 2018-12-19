class myScanner {

    private static java.util.Scanner input = new java.util.Scanner(System.in);

    static String readLine(String string){
        System.out.println(string);
        String x = input.nextLine();

        while (x.trim().length() == 0) {
            System.out.println(string);
            x=input.nextLine();
        }

        return x;
    }

    static double readDouble(String string){
        System.out.println(string);
        while (!(input.hasNextDouble())){
            System.out.println(string);
            input.nextLine();
        }
        double x= input.nextDouble();
        input.nextLine();
        return x;
    }

    static int readInt(String string){
        System.out.println(string);
        while (!(input.hasNextInt())){
            System.out.println(string);
            input.nextLine();
        }
        int x = input.nextInt();
        input.nextLine();
        return x;
    }

    static int readInt(){
        while (!(input.hasNextInt())){
            System.out.println("Please enter an integer!");
            input.nextLine();
        }
        int x = input.nextInt();
        input.nextLine();
        return x;
    }



    static void printDone(){
        System.out.println("Done!");
    }

    static void nextLine(){
        input.nextLine();
    }

}