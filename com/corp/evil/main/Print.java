import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;

public class Print {

    private static final int DEFAULT = 0;
    private static final int PROJECT_LOADED = 1;
    private static final int IO_EXCEPTION = -1;
    private static final int PROJECT_STARTED = 2;

    // reasons to quit
    private static final int NO_PROJECT = 1;

    private static StringBuilder sb = new StringBuilder();
    public static String newline = System.lineSeparator();
    private static final PrintStream out = System.out;

    public static int printStartMenu() {

        sb.append("Welcome to the Project Planning Software of Evil Corp" + newline);
        sb.append("Choose one of the following options" + newline);
        sb.append("➤ 1. Load a existing project" + newline);
        sb.append("➤ 2. Create a new project");

        out.println(sb);
        sb.setLength(0);

        return myScanner.readInt();
    }

    public static int loadProject() {
        Project project;
        try {
            project = JsonReaderWriter.load(Project.class);
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


    public static int printPrimaryMeny() {
        sb.append("Choose an option from below" + newline);
        sb.append("➤ 1. Project" + newline);
        sb.append("➤ 2. Teams and Members" + newline);
        sb.append("➤ 3. Tasks Management" + newline);
        sb.append("➤ 4. Risks Management" + newline);
        sb.append("➤ 5. Budget" + newline);
        sb.append("➤ 6. Save and Exit" + newline);
        System.out.println(sb);
        sb.setLength(0);
        return myScanner.readInt();
    }

    public static int printProjectMenu() {
        sb.append("➤ 1. Print Project" + newline);
        sb.append("➤ 2. Edit Project ..." + newline);
        System.out.println(sb);
        sb.setLength(0);
        return myScanner.readInt();
    }

    public static int printEditProjectMenu() {
        sb.append("➤ 1. Update the project's name" + newline);
        sb.append("➤ 2. Update the project's end date" + newline);
        System.out.println(sb);
        sb.setLength(0);
        return myScanner.readInt();
    }


    public static int printTeamMenu() {
        sb.append("➤ 1. Print All Members" + newline);
        sb.append("➤ 2. Print All Teams" + newline);
        sb.append("➤ 3. Add member to the project" + newline);
        sb.append("➤ 4. Update member's information" + newline);
        sb.append("➤ 5. Remove member from the project" + newline);
        sb.append("➤ 6. Create a sub-team" + newline);
        sb.append("➤ 7. Edit a sub-team" + newline);
        System.out.println(sb);
        sb.setLength(0);
        return myScanner.readInt();
    }

    public static int printEditSubTeamMenu() {
        sb.append("Choose what you want to edit"+newline);
        sb.append("➤ 1. Update the team's name "+newline);
        sb.append("➤ 2. Add a member"+newline);
        sb.append("➤ 3. Remove a member "+newline);
        System.out.println(sb);
        sb.setLength(0);
        return myScanner.readInt();
    }

    public static int printEditMemberMenu() {
        sb.append("➤ 1. Update the member's name" + newline);
        sb.append("➤ 2. Update the members's salary" + newline);
        System.out.println(sb);
        sb.setLength(0);
        return myScanner.readInt();
    }

    public static int printTasksMenu() {
        sb.append("➤ 1. Print All Tasks" + newline);
        sb.append("➤ 2. Add a task" + newline);
        sb.append("➤ 3. Edit a task" + newline);
        sb.append("➤ 4. Remove a task" + newline);
        sb.append("➤ 5. Assign a task to a team" + newline);
        sb.append("➤ 6. Update the time spent on a task" + newline);
        System.out.println(sb);
        sb.setLength(0);
        return myScanner.readInt();
    }

    public static int printRiskMenu() {
        sb.append("Choose an option from below"+newline);
        sb.append("➤ 1. Print the Risk Matrix"+newline);
        sb.append("➤ 2. Add a risk"+newline);
        sb.append("➤ 3. Remove a risk"+newline);
        System.out.println(sb);
        sb.setLength(0);
        return myScanner.readInt();
    }

    public static Activity createActivity(){
        String name = myScanner.readLine("Enter the name of the risk: ");
        int startWeek = myScanner.readInt("Enter the start week: ");
        int startYear = myScanner.readInt("Enter the start Year: ");
        int endWeek = myScanner.readInt("Enter the end week: ");
        int endYear = myScanner.readInt("Enter the end year: ");

        return new Activity(name, startWeek, startYear, endWeek, endYear);
    }

    public static Risk createRisk(){
        String name = myScanner.readLine("Enter the name of the risk: ");
        int probability = myScanner.readInt("Enter risk probability: ");
        int impact = myScanner.readInt("Enter risk impact: ");
        Risk risk = null;
        try {
            risk = new Risk(name, probability, impact);
        } catch (RiskProbabilityNotDefinedException e) {
            e.printStackTrace();
        } catch (RiskImpactNotDefinedException e) {
            e.printStackTrace();
        }
        return risk;
    }

    public static Project createProject(){
        String name = myScanner.readLine("Please enter the name of your new project:");
        int startYear = myScanner.readInt("Please enter the start year of your project.");
        int endYear = myScanner.readInt("Please enter the end year of your project.");
        int startWeek = myScanner.readInt("Please enter the start week of your project.");
        int endWeek = myScanner.readInt("Please enter the end week of your project.");

        ProjectSchedule schedule = new ProjectSchedule(startYear, startWeek, endYear, endWeek);

        Project project = new Project(name, schedule);

        return project;
    }

    public static String addName(){
        String name = myScanner.readLine("Enter the name");
        return name;
    }

    public static LocalDateTime ender(){
        int endYear = myScanner.readInt("Please enter the end year of your project.");
        int endWeek = myScanner.readInt("Please enter the end week of your project.");
        LocalDateTime date = ProjectSchedule.getLocalDateTime(endYear, endWeek, ProjectSchedule.LAST_WORKDAY, ProjectSchedule.DAY_END_HOUR);
        return date;
    }


    public static Member readMember() {
        String name = myScanner.readLine("Please enter the name of your new member:");
        double salary = myScanner.readDouble("Please enter the salary of the new member:");
        try {
            Member member = new Member(name, salary);
            return member;
        } catch (NameIsEmptyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Team readTeam() {
     String name = myScanner.readLine("Enter the name of the team: ");
            Team team = new Team(name);
            return team;

    }




    public static Member chooseMember(Team team) {

        team.getMembers();

        sb.append("Choose a member to remove by enter his/her name");
        System.out.println(sb.toString());


        //TODO: print all members and let the user pick one

        return null;
    }


    public static Activity removeActivity() {

        // TODO: print options to chose the activity to be removed

        return null;
    }
}

