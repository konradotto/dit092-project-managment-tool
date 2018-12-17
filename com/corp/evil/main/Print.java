import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Print {

    private final static int DEFAULT = 0;
    private final static int PROJECT_LOADED = 1;
    private final static int IO_EXCEPTION = -1;
    private final static int PROJECT_STARTED = 2;

    // reasons to quit
    private final static int NO_PROJECT = 1;

    private static StringBuilder sb = new StringBuilder();
    private static String newline = System.lineSeparator();
    private final static PrintStream out = System.out;
    private final static Scanner input = new Scanner(System.in);

    public static int printStartMenu() {

        sb.append("Welcome to the Project Planning Software of Evil Corp" + newline);
        sb.append("Press one of the follwing options from the list" + newline);
        sb.append("➤ 1. Load a existing project" + newline);
        sb.append("➤ 2. Create an empty project");

        out.println(sb);
        sb.setLength(0);

        return readInt();
    }

    public static int loadProject() {
        Project project = null;

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("select a JSON-project to load");
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return false;
                } else {
                    String filename = file.getName().toLowerCase();
                    return filename.endsWith(".json");
                }
            }

            @Override
            public String getDescription() {
                return "JSON Files (*.json)";
            }
        };
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        File jsonFile = chooser.getSelectedFile();

        try {
            project = JsonReaderWriter.fromJsonFile(jsonFile, Project.class);
        } catch (IOException e) {
            return IO_EXCEPTION;
        }

        ConsoleProgram.setProject(project);
        return PROJECT_LOADED;
    }

    public static int startProject() {
        Project project = null;

        ConsoleProgram.setProject(project);
        return PROJECT_STARTED;
    }

    private static int readInt() {
        int choice = DEFAULT;
        do {
            String in = input.nextLine();
            try {
                choice = Integer.parseInt(in);
            } catch (NumberFormatException e) {
                out.println("The input could not be parsed to an Integer. Try again.");
            }
        } while (choice == DEFAULT);

        return choice;
    }

    public static int printEditing() {
        sb.append("Choose what you want to edit" + newline);
        sb.append("➤ 1. Project Name" + newline);
        sb.append("➤ 2. Project Team" + newline);
        sb.append("➤ 3. Project riskMatrix" + newline);
        sb.append("➤ 4. Project Schedule" + newline);
        sb.append("➤ 5. Exit" + newline);

        System.out.println(sb);
        sb.setLength(0);

        return input.nextInt();
    }

    public static String printEditingName() {

        sb.append("Please enter a new name for your project: "+newline);

        System.out.println(sb);
        sb.setLength(0);

        return input.nextLine();
    }

    public void printAllTeams(){

        //System.out.println(Team.getName());
    }

    public static int printEditingTeam() {

        //TODO Add a method to retrive desired team

        sb.append("Choose what you want to edit"+newline);
        sb.append("➤ 1. Add member "+newline);
        sb.append("➤ 2. Remove member "+newline);
        sb.append("➤ 3. Add activity "+newline);
        sb.append("➤ 4. Remove activity "+newline);

        System.out.println(sb);
        sb.setLength(0);

        return input.nextInt();
    }

    public static int printEditingRiskMatrix() {

        sb.append("Choose what you want to edit"+newline);
        sb.append("➤ 1. Add risk"+newline);
        sb.append("➤ 2. Remove risk"+newline);

        System.out.println(sb.toString());
        sb.setLength(0);

        return input.nextInt();
    }

    public static int printEditingSchedule() {

        sb.append("Choose what you want to edit"+newline);
        sb.append("➤ 1. Add activity"+newline);
        sb.append("➤ 2. Remove activity"+newline);


        System.out.println(sb.toString());
        sb.setLength(0);

        return input.nextInt();
    }

    public static Member addMember() {

        sb.append("Please enter the name of your new member:");
        System.out.println(sb.toString());
        sb.setLength(0);
        String name = input.nextLine();
        sb.append("Please enter the salary of the new member:");
        out.println(sb.toString());
        sb.setLength(0);
        double salary = input.nextDouble();

        try {
            Member member = new Member(name, salary);
        } catch (NameIsEmptyException e) {
            e.printStackTrace();
        }
        // Todo: ask for member information and add him/her


        return null;

    }

    public static Member chooseMember(Team team) {

        team.getMembers();

        sb.append("Choose a member to remove by enter his/her name");
        System.out.println(sb.toString());


        //TODO: print all members and let the user pick one

        return null;
    }

    public static Activity createActivity() {

        // TODO: ask for activity properties and create new activity according to them

        return null;
    }

    public static Activity removeActivity() {

        // TODO: print options to chose the activity to be removed

        return null;
    }
}

