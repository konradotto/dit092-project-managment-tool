import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Print {

    private static final int SEPARATOR_WIDTH = 50;

    private static final int DEFAULT = 0;
    public static final int PROJECT_LOADED = 1;
    private static final int IO_EXCEPTION = -1;
    private static final int PROJECT_STARTED = 2;
    private static final int NOT_A_PROJECT = -2;

    // reasons to quit
    private static final int NO_PROJECT = 1;

    private static StringBuilder sb = new StringBuilder();
    public static String LS = System.lineSeparator();
    private static final PrintStream out = System.out;

    /**
     * Function printing the StartMenu and reading the user's choice.
     *
     * @return Integer value entered by the user
     */
    public static int printStartMenu() {
        sb.append("Welcome to the Project Planning Software of Evil Corp" + LS);
        sb.append("Choose one of the following options" + LS);
        sb.append("➤ 1. Load an existing project" + LS);
        sb.append("➤ 2. Create a new project" + LS);
        sb.append("➤ 3. Exit the program");

        printBuffer();

        return myScanner.readInt();
    }


    /**
     * Function to choose a file and attempt to load a project from it.
     *
     * @return value depending on the success of the attempt
     */
    public static int loadProject() {
        Project project;
        try {
            project = JsonReaderWriter.load(Project.class);
        } catch (IOException e) {
            return IO_EXCEPTION;
        }

        if (!project.isProject()) {
            return NOT_A_PROJECT;
        }
        project.offerFileChange();
        ConsoleProgram.setProject(project);
        return PROJECT_LOADED;
    }

    /**
     * Function starting the dialogue to create a new project.
     *
     * @return Integer depending on the success
     */
    public static int startProject() {
        Project project = null;

        ConsoleProgram.setProject(project);
        return PROJECT_STARTED;
    }


    public static int exitProgram(boolean projectUsed) {
        sb.append(String.join("", Collections.nCopies(SEPARATOR_WIDTH, "*")) + LS);
        sb.append("The Console Application is being terminated." + LS);
        if (projectUsed) {
            sb.append("All changes made to the project are being saved." + LS);
            sb.append(LS);
            sb.append("Thank you for trusting us with your project!" + LS);
        }
        sb.append("This application is brought to you by Evil Corp." + LS);
        sb.append(String.join("", Collections.nCopies(SEPARATOR_WIDTH, "*")));

        printBuffer();
        return 0;
    }


    public static int printPrimaryMenu() {
        // Specify String representation of primary menu
        sb.append("Choose an option from below" + LS);
        sb.append("➤ 1. Project" + LS);
        sb.append("➤ 2. Teams and Members" + LS);
        sb.append("➤ 3. Tasks Management" + LS);
        sb.append("➤ 4. Risks Management" + LS);
        sb.append("➤ 5. Budget" + LS);
        sb.append("➤ 6. Save and Exit" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static int printProjectMenu() {
        sb.append("➤ 1. Print Project" + LS);
        sb.append("➤ 2. Edit Project ..." + LS);
        sb.append("➤ 3. Back to the main" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static int printEditProjectMenu() {
        sb.append("➤ 1. Update the project's name" + LS);
        sb.append("➤ 2. Update the project's end date" + LS);
        sb.append("➤ 3. Back to the previous menu" + LS);

        printBuffer();
        return myScanner.readInt();
    }


    public static int printTeamMenu() {
        sb.append("➤ 1. Print All Members" + LS);
        sb.append("➤ 2. Print All Teams" + LS);
        sb.append("➤ 3. Add member to the project" + LS);
        sb.append("➤ 4. Update member's information" + LS);
        sb.append("➤ 5. Remove member from the project" + LS);
        sb.append("➤ 6. Create a sub-team" + LS);
        sb.append("➤ 7. Edit a sub-team" + LS);
        sb.append("➤ 8. Back to the main" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static int printEditSubTeamMenu() {
        sb.append("Choose what you want to edit" + LS);
        sb.append("➤ 1. Update the team's name " + LS);
        sb.append("➤ 2. Add a member" + LS);
        sb.append("➤ 3. Remove a member " + LS);
        sb.append("➤ 4. Back to the previous menu" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static int printEditMemberMenu() {
        sb.append("➤ 1. Update the member's name" + LS);
        sb.append("➤ 2. Update the members's salary" + LS);
        sb.append("➤ 3. Back to the previous menu" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static int printTasksMenu() {
        sb.append("➤ 1. Print All Tasks" + LS);
        sb.append("➤ 2. Add a task" + LS);
        sb.append("➤ 3. Edit a task" + LS);
        sb.append("➤ 4. Remove a task" + LS);
        sb.append("➤ 5. Assign a task to a team" + LS);
        sb.append("➤ 6. Update the time spent on a task" + LS);
        sb.append("➤ 7. Back to the main" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static int printEditTaskMenu(){
        sb.append("➤ 1. Edit a task name" + LS);
        sb.append("➤ 2. Edit a task end week" + LS);
        sb.append("➤ 3. Edit a task end year" + LS);
        sb.append("➤ 4. Back to the previous menu" + LS);

        printBuffer();
        return myScanner.readInt();
    }


    public static int printRiskMenu() {
        sb.append("Choose an option from below" + LS);
        sb.append("➤ 1. Print the Risk Matrix" + LS);
        sb.append("➤ 2. Add a risk" + LS);
        sb.append("➤ 3. Remove a risk" + LS);
        sb.append("➤ 4. Back to the main" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static Activity createActivity(){
        String name = myScanner.readLine("Enter the name of the task: ");
        int startWeek = myScanner.readInt("Enter the start week: ");
        while (!checkWeeks(startWeek)){
            startWeek = myScanner.readInt("Enter the start week: ");
        }
        int startYear = myScanner.readInt("Enter the start Year: ");
        int endWeek = myScanner.readInt("Enter the end week: ");
        while (!checkWeeks(endWeek)){
            endWeek = myScanner.readInt("Enter the start week: ");
        }
        int endYear = myScanner.readInt("Enter the end year: ");

        while (!checkYears(startYear,endYear)){
            startYear = myScanner.readInt("Enter the start Year: ");
            endYear = myScanner.readInt("Enter the end year: ");
        }

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
        int startWeek = myScanner.readInt("Please enter the start week of your project.");
        while (!checkWeeks(startWeek)){
            startWeek = myScanner.readInt("Please enter the start week of your project.");
        }
        int endYear = myScanner.readInt("Please enter the end year of your project.");
        int endWeek = myScanner.readInt("Please enter the end week of your project.");
        while (!checkWeeks(endWeek)){
            endWeek = myScanner.readInt("Please enter the end week of your project.");
        }

        while (!checkYears(startYear,endYear)){
            startYear = myScanner.readInt("Please enter the start year of your project.");
            endYear = myScanner.readInt("Please enter the end year of your project.");
        }


        ProjectSchedule schedule = new ProjectSchedule(startYear, startWeek, endYear, endWeek);

        Project project = new Project(name, schedule);

        return project;
    }

    public static String enterName(){
        String name = myScanner.readLine("Enter the intended name:");
        return name;
    }

    public static LocalDateTime ender(){
        int endYear = myScanner.readInt("Please enter the end year of your project.");
        int endWeek = myScanner.readInt("Please enter the end week of your project.");
        while (!checkWeeks(endWeek)){
            endWeek = myScanner.readInt("Please enter the end week of your project.");
        }
        LocalDateTime date = ProjectSchedule.getLocalDateTime(endYear, endWeek, ProjectSchedule.LAST_WORKDAY, ProjectSchedule.DAY_END_HOUR);
        return date;
    }


    public static Member createMember() {
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

    public static Team createTeam() {
        String name = myScanner.readLine("Enter the name of the team: ");
        Team team = new Team(name);
        return team;

    }

    public static Member readMember() throws MemberIsNullException {
        String option = myScanner.readLine("Choose how to retrieve a member:\n1) By entering the member's name\n2) By choosing a member from the list");
        while (!(option.equals("1") || option.equals("2"))){
            option = myScanner.readLine("Choose how to retrieve a member:\n1) By entering the member's name\n2) By choosing a member from the list");
        }
        switch (option) {
            case "1":
                Member member = ConsoleProgram.retrieveMember(enterName());
                if (member==null){
                    throw new MemberIsNullException("No such member!");
                }
                return member;
            default:
                return readMemberFromList();
        }
    }


    public static Member readMemberFromList() throws MemberIsNullException {
        ArrayList<Member> members = ConsoleProgram.getProject().getTeam().getMembers();
        if (members.isEmpty()){
            throw new MemberIsNullException("No registered members!");
        }
        members.sort(Comparator.comparing(Member::getName));
        for (int i = 0; i<members.size(); i++){
            System.out.println(i+") "+members.get(i).getName());
        }
        int j = myScanner.readInt("Choose a member from the list: ");
        while (j>=members.size()){
            j = myScanner.readInt("Choose a valid option! ");
        }
        return members.get(j);

    }


    public static Team readTeam() throws TeamIsNullException {
        String option = myScanner.readLine("Choose how to retrieve a team:\n1) By entering the team's name\n2) By choosing a team from the list");
        while (!(option.equals("1") || option.equals("2"))){
            option = myScanner.readLine("Choose how to retrieve a team:\n1) By entering the team's name\n2) By choosing a team from the list");
        }
        switch (option) {
            case "1":
                Team team = ConsoleProgram.retrieveTeam(enterName());
                if (team==null){{ throw new TeamIsNullException("No such team!"); }
                }
                return team;
            default:
                return readTeamFromList();
        }
    }

    public static Team readTeamFromList() throws TeamIsNullException {
        ArrayList<Team> teams = ConsoleProgram.getProject().getTeams();
        if (teams.isEmpty()){
            throw new TeamIsNullException("No registered teams!");
        }
        teams.sort(Comparator.comparing(Team::getName));
        for (int i = 0; i < teams.size(); i++) {
            System.out.println(i + ") " + teams.get(i).getName());
        }
        int j = myScanner.readInt("Choose a team from the list: ");
        while (j>=teams.size()){
            j = myScanner.readInt("Choose a valid option! ");
        }
        return teams.get(j);
    }

    public static Activity readActivity() throws ActivityIsNullException {
        String option = myScanner.readLine("Choose how to retrieve a task:\n1) By typing the task's name\n2) By choosing a task from the list");
        while (!(option.equals("1") || option.equals("2"))){
            option = myScanner.readLine("Choose how to retrieve a task:\n1) By typing the tasks's name\n2) By choosing a task from the list");
        }
        switch (option) {
            case "1":
                Activity activity =ConsoleProgram.retrieveActivity(enterName());
                if (activity==null){
                    throw new ActivityIsNullException("No such task!");
                }
                return activity;
            default:
                return readActivityFromList();
        }
    }

    public static Activity readActivityFromList() throws ActivityIsNullException {
        ArrayList<Activity> activities = ConsoleProgram.getProject().getSchedule().getActivities();
        if (activities.isEmpty()){
            throw new ActivityIsNullException("No registered tasks!");
        }
        activities.sort(Comparator.comparing(Activity::getName));
        for (int i = 0; i < activities.size(); i++) {
            System.out.println(i + ") " + activities.get(i).getName());
        }
        int j = myScanner.readInt("Choose an activity from the list: ");
        while (j>=activities.size()){
            j = myScanner.readInt("Choose a valid option! ");
        }
        return activities.get(j);
    }

    public static Risk readRisk() throws RiskIsNullException {
        String option = myScanner.readLine("Choose how to retrieve a risk:\n1) By entering the risk's name\n2) By choosing a risk from the list");
        while (!(option.equals("1") || option.equals("2"))){
            option = myScanner.readLine("Choose how to retrieve a member:\n1) By entering the risk's name\n2) By choosing a risk from the list");
        }
        switch (option) {
            case "1":
                Risk risk = ConsoleProgram.retrieveRisk(enterName());
                if (risk==null){
                    throw new RiskIsNullException("No such risk!");
                }
                return risk;
            default:
                return readRiskFromList();
        }
    }

    public static Risk readRiskFromList() throws RiskIsNullException {
        ArrayList<Risk> risks = ConsoleProgram.getProject().getRiskMatrix().getRisks();
        if (risks.isEmpty()){
            throw new RiskIsNullException("No registered risks!");
        }
        risks.sort(Comparator.comparing(Risk::getRiskName));
        for (int i = 0; i < risks.size(); i++) {
            System.out.println(i + ") " + risks.get(i).getRiskName());
        }
        int j = myScanner.readInt("Choose a risk from the list: ");
        while (j>=risks.size()){
            j = myScanner.readInt("Choose a valid option! ");
        }
        return risks.get(j);
    }


    public static void println(String s) {
        out.println(s);
    }

    private static void printBuffer() {
        out.println(sb);        // print buffer
        sb.setLength(0);        // empty buffer
    }

    private static boolean checkWeeks(int weeks){

        if (weeks>52){
            println("The year consists of 52 weeks only!" + LS);
            return false;
        }
        else if (weeks<1){
            println("The first week of the year is week 1!" + LS);
            return false;
        }
        return true;
    }

    private static boolean checkYears(int num1,int num2){

        if (num1>num2){
            println("The end year cannot be lower than the start year" + LS);
            return false;
        }

        return true;
    }

    public static void defaultMonologue() {
        out.println("Choose a valid option!" + LS);
    }
}

