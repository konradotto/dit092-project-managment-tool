import java.io.IOException;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Print {

    private static final int SEPARATOR_WIDTH = 50;

    public static final int PROJECT_LOADED = 1;
    private static final int IO_EXCEPTION = -1;
    private static final int PROJECT_STARTED = 2;
    private static final int NOT_A_PROJECT = -2;

    private static final int SEARCH_FOR_TASK = 1;
    private static final int SELECT_TASK = 2;

    private static final int SEARCH_FOR_MEMBER = 1;
    private static final int SELECT_MEMBER = 2;

    private static final int SEARCH_FOR_RISK = 1;
    private static final int SELECT_RISK = 2;

    private static StringBuilder sb = new StringBuilder();
    static final String LS = System.lineSeparator();
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
            Print.println("No project could be loaded from the specified file." + LS);
            return IO_EXCEPTION;
        }

        if (!project.isProject()) {
            Print.println("The object loaded from the json-file is no project." + LS);
            return NOT_A_PROJECT;
        }
        project.solveObjectCopies();

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


    /**
     * Function specifying what to print when the program is closed
     *
     * @param projectUsed
     * @return
     */
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
        sb.append("➤ 5. Budget Management" + LS);
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
        sb.append("➤ 4. Edit or display a member" + LS);
        sb.append("➤ 5. Remove member from the project" + LS);
        sb.append("➤ 6. Create a Team" + LS);
        sb.append("➤ 7. Edit a Team" + LS);
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
        sb.append("➤ 3. Print the time worked by this team member" + LS);
        sb.append("➤ 4. Back to the previous menu" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static int printTasksMenu() {
        sb.append("➤ 1. Print All Tasks" + LS);
        sb.append("➤ 2. Add a task" + LS);
        sb.append("➤ 3. Edit a task" + LS);
        sb.append("➤ 4. Back to the main" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static int printEditTaskMenu() {
        sb.append("➤ 1. Remove this task" + LS);
        sb.append("➤ 2. Edit task name" + LS);
        sb.append("➤ 3. Edit task end week" + LS);
        sb.append("➤ 4. Edit task end year" + LS);
        sb.append("➤ 5. Assign a team to this task" + LS);
        sb.append("➤ 6. Work on this task" + LS);
        sb.append("➤ 7. Back to the previous menu" + LS);

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

    public static int printProjectBudgetMenu() {
        sb.append("Choose an option from below" + LS);
        sb.append("➤ 1. Print the Budget" + LS);
        sb.append("➤ 2. Print the Earned Value" + LS);
        sb.append("➤ 3. Print the Cost Variance" + LS);
        sb.append("➤ 4. Print the Schedule Variance" + LS);
        sb.append("➤ 5. Change the date used for determining the Schedule Variance" + LS);
        sb.append("➤ 6. Back to the main" + LS);

        printBuffer();
        return myScanner.readInt();
    }

    public static Activity createActivity() {
        String name = myScanner.readLine("Enter the name of the task: ");
        return new Activity(name, readTimePeriod("activity"), null,
                myScanner.readInt("Please enter the estimated effort in hours: "));
    }


    public static Risk createRisk() {
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


    /**
     * Function reading the name and dates to create a new project
     *
     * @return the project created with the collected data
     */
    public static Project createProject() {
        String name = myScanner.readLine("Please enter the name of the new project:");
        try {
            return new Project(name, new ProjectSchedule(readTimePeriod("project")));       // TODO: clean-up this mess
        } catch (NameIsEmptyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String enterName() {
        String name = myScanner.readLine("Enter the intended name:");
        return name;
    }

    /**
     * Method reading the start year, start week, end year and end week of a TimePeriod repeatedly
     * until the values are successfully used to initialize a TimePeriod.
     *
     * @param purpose a String telling the user what he is entering the dates for
     * @return the resulting TimePeriod
     */
    public static TimePeriod readTimePeriod(String purpose) {
        int startYear = myScanner.readInt("Enter the start year of the " + purpose + ": ");
        int startWeek = myScanner.readInt("Enter the start week of the " + purpose + ": ");
        int endYear = myScanner.readInt("Enter the end year of the " + purpose + ": ");
        int endWeek = myScanner.readInt("Enter the end week of the " + purpose + ": ");

        try {
            return new TimePeriod(startYear, startWeek, endYear, endWeek);
        } catch (IllegalArgumentException e) {
            Print.println(e + LS);
            return readTimePeriod(purpose);             // recursively call of readTimePeriod until it succeeds
        }
    }

    public static YearWeek pickEndWeek(String purpose) {
        YearWeek endWeek;
        int year = myScanner.readInt("Please enter the end year of the " + purpose + ".");
        int week = myScanner.readInt("Please enter the end week of the " + purpose + ".");

        try {
            endWeek = new YearWeek(year, week);         // try whether the entered end week works

        } catch (IllegalArgumentException e) {
            println(e.getMessage() + LS);
            endWeek = null;
        }
        return endWeek;
    }


    public static Member createMember() {
        String name = myScanner.readLine("Please enter the name of the new member:");
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
        Team team = null;
        try {
            team = new Team(name);
        } catch (NameIsEmptyException e) {          //TODO: fix this mess
            e.printStackTrace();
        }
        return team;

    }


    public static int chooseMemberSelection() {
        sb.append("Please choose a way to select a member:" + LS);
        sb.append("➤ 1. Enter the members name" + LS);
        sb.append("➤ 2. Choosing from the list of members" + LS);

        printBuffer();
        return myScanner.readInt();
    }


    public static Member chooseMemberByName(Team team) {
        String name = myScanner.readLine("Please enter the member's full name.");
        if (name == null) {
            return null;
        }
        return team.retrieveMember(name);
    }

    public static Member chooseMemberFromList(Team team) {
        sb.append("Please choose a member from the list:" + LS);
        sb.append(team.toNumberedString());

        printBuffer();
        return team.retrieveMember(myScanner.readInt() - 1);
    }


    public static Team readTeam() throws TeamIsNullException {
        switch (myScanner.readInt("Choose how to retrieve a team:" + LS +
                "1) By entering the team's name" + LS +
                "2) By choosing a team from the list")) {
            case SEARCH_FOR_MEMBER:
                Team team = ConsoleProgram.retrieveTeam(enterName());
                if (team == null) {
                    throw new TeamIsNullException("No such team registered!");
                }
                return team;
            case SELECT_MEMBER:
            default:
                return readTeamFromList();
        }
    }

    public static Team readTeamFromList() throws TeamIsNullException {
        ArrayList<Team> teams = ConsoleProgram.getProject().getTeams();
        if (teams.isEmpty()) {
            throw new TeamIsNullException("No teams registered for the project!");
        }
        teams.sort(Comparator.comparing(Team::getName));

        for (int i = 0; i < teams.size(); i++) {
            Print.println(i + 1 + ") " + teams.get(i).getName());
        }
        int j = myScanner.readInt("Choose a team from the list: ");
        while (j > teams.size() || j < 1) {
            j = myScanner.readInt("Choose a valid option! ");
        }
        return teams.get(j - 1);
    }

    public static Activity readActivity() throws ActivityIsNullException {
        switch (myScanner.readInt("Choose how to retrieve a task:" + LS +
                "1) By typing the tasks's name" + LS +
                "2) By choosing a task from the list")) {
            case SEARCH_FOR_TASK:
                Activity activity = ConsoleProgram.retrieveActivity(enterName());
                if (activity == null) {
                    throw new ActivityIsNullException("No such activity registered!");
                }
                return activity;
            case SELECT_TASK:
            default:
                return readActivityFromList();
        }
    }

    /**
     * Method to select an activity from the List of all activities
     *
     * @return
     */
    public static Activity readActivityFromList() throws ActivityIsNullException {
        ArrayList<Activity> activities = ConsoleProgram.getProject().getSchedule().getActivities();
        if (activities.isEmpty()) {
            throw new ActivityIsNullException("No activities registered for the project!");
        }
        activities.sort(Comparator.comparing(Activity::getName));

        for (int i = 0; i < activities.size(); i++) {
            Print.println(i + 1 + ") " + activities.get(i).getName());
        }
        int j = myScanner.readInt("Choose an activity from the list: ");
        while (j > activities.size() || j < 1) {
            j = myScanner.readInt("Choose a valid option! ");
        }
        return activities.get(j - 1);
    }

    public static Risk readRisk() throws RiskIsNullException {
        switch (myScanner.readInt("Choose how to retrieve a risk:" + LS +
                "1) By entering the risk's name" + LS +
                "2) By choosing a risk from the list")) {
            case SEARCH_FOR_RISK:
                Risk risk = ConsoleProgram.retrieveRisk(enterName());
                if (risk == null) {
                    throw new RiskIsNullException("No such risk!");
                }
                return risk;
            case SELECT_RISK:
            default:
                return readRiskFromList();
        }
    }

    public static Risk readRiskFromList() throws RiskIsNullException {
        ArrayList<Risk> risks = ConsoleProgram.getProject().getRiskMatrix().getRisks();
        if (risks.isEmpty()) {
            throw new RiskIsNullException("No risks registered for the project!");
        }
        risks.sort(Comparator.comparing(Risk::getRiskName));

        for (int i = 0; i < risks.size(); i++) {
            System.out.println(i + 1 + ") " + risks.get(i).getRiskName());
        }
        int j = myScanner.readInt("Choose a risk from the list: ");
        while (j > risks.size() || j < 1) {
            j = myScanner.readInt("Choose a valid option! ");
        }
        return risks.get(j - 1);
    }


    public static void println(String s) {
        out.println(s);
    }

    private static void printBuffer() {
        out.println(sb);        // print buffer
        sb.setLength(0);        // empty buffer
    }

    public static void defaultMonologue() {
        out.println("Choose a valid option!" + LS);
    }

    public static YearWeek readYearWeek() {
        int year = myScanner.readInt("Enter the year your week is in: ");
        int week = myScanner.readInt("Enter the week of the year: ");
        try {
            return new YearWeek(year, week);
        } catch (IllegalArgumentException e) {
            println(e.getMessage());
            return null;
        }
    }

    public static DayOfWeek readDayOfWeek() {
        int cnt = 0;
        do {
            println("Choose a weekday for your date: ");
            switch (myScanner.readInt(
                    "1) Monday" + LS +
                            "2) Tuesday" + LS +
                            "3) Wednesday" + LS +
                            "4) Thursday" + LS +
                            "5) Friday" + LS +
                            "6) Saturday" + LS +
                            "7) Sunday")) {
                case 1:
                    return DayOfWeek.MONDAY;
                case 2:
                    return DayOfWeek.TUESDAY;
                case 3:
                    return DayOfWeek.WEDNESDAY;
                case 4:
                    return DayOfWeek.THURSDAY;
                case 5:
                    return DayOfWeek.FRIDAY;
                case 6:
                    return DayOfWeek.SATURDAY;
                case 7:
                    return DayOfWeek.SUNDAY;
                default:
                    println("Illegal selection. Try again!");
                    cnt++;
            }
        } while (cnt < 3);
        return null;
    }
}