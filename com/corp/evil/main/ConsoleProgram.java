/**
 * Class responsible to keep track of the current state (finite state machine of menus),
 * input-output and hosting the project.
 */
public class ConsoleProgram {

    // status constants
    private static final int NO_PROJECT = -1;

    private static final int PROJECT = 1;
    private static final int MAIN = 2;
    private static final int END = 3;
    private static final int SUCCESS = 42;
    private static final int LOAD = 1;
    private static final int NEW = 2;

    private static final int PRINT_PROJET = 1;
    private static final int EDIT_PROJECT = 2;

    private static final int PRIMARY_PROJECT = 1;
    private static final int PRIMARY_TEAM = 2;
    private static final int PRIMARY_TASK = 3;
    private static final int PRIMARY_RISK = 4;
    private static final int PRIMARY_BUDGET = 5;
    private static final int PRIMARY_SAVE_EXIT = 6;

    // result constants
    private static final int PROJECT_CREATED = 1;

    // members
    private static Project project;
    private static boolean proceed = true;

    //TODO error handling


    /**
     * Function to run the ConsoleProgram.
     * Start by choosing a project.
     * Afterwards the main menu is entered and looped.
     *
     * @return value depending on whether the program has been completed without complications
     */
    public static int run() {
        // set entry point for the console program
        int position = PROJECT;

        do {
            switch (position) {
                case PROJECT:       // print start menu and choose whether to load or create a new project
                    position = loadOrNewProject();
                    break;
                case MAIN:
                    position = primaryMenu();
                    break;
                case END:
                    endConsoleProgram();
                    proceed = false;
                    break;
                default:
                    break;
            }
        } while (proceed);

        return SUCCESS;     // return success after finishing the program
    }

    /**
     * Function to load an existing project or create a new one.
     *
     * @return next point in the program to continue from
     */
    private static int loadOrNewProject() {
        int next = MAIN;
        switch (Print.printStartMenu()) {
            case LOAD:
                if (Print.loadProject() != Print.PROJECT_LOADED) {
                    next = PROJECT;
                    Print.println("Loading the Project failed. Try again!" + Print.newline);
                }
                break;
            case NEW:
                if (!(createProject() == PROJECT_CREATED)) {
                    next = PROJECT;
                }
                break;
            case END:
                next = END;
                break;

            default:
                Print.println("Choose a valid option!" + Print.newline);
                next = PROJECT;
                break;
        }

        return next;
    }


    public static int primaryMenu() {
        int next = MAIN;
        switch (Print.printPrimaryMenu()) {
            case PRIMARY_PROJECT:
                projectMenu();
                break;
            case PRIMARY_TEAM:
                teamMenu();
                break;
            case PRIMARY_TASK:
                taskManager();
                proceed = false;
                break;
            case PRIMARY_RISK:
                riskManager();
                break;
            case PRIMARY_BUDGET:
                if (project.getBudget().isEmpty()) {
                    System.out.println("No budget data yet!");
                } else {
                    System.out.println(project.getBudget());
                }
                break;
            case PRIMARY_SAVE_EXIT:
                next = END;
                break;
            default:
                System.out.println("Choose a valid option!\n");
                next = MAIN;
                break;
        }
        return next;
    }


    private static void endConsoleProgram() {
        Print.exitProgram();
        project.saveProject();
    }


    public static void taskManager() {
        do {
            switch (Print.printTasksMenu()) {
                case 1:
                    if (project.getSchedule().getActivities().isEmpty()) {
                        System.out.println("No registered tasks" + Print.newline);
                    } else {
                        System.out.println(project.getSchedule());
                    }
                    proceed = false;
                    break;
                case 2:
                    try {
                        project.getSchedule().addActivity(Print.createActivity());
                        //TODO: Running the line above is throwing a null pointer exception.

                        // The first solution is that we need to fix the no-team constructor,
                        // and to create a different toString method for activities without the teams assigned to them.
                        // The calculations can be skipped (I guess) unless there is a non-empty team assigned to the activity.

                        // An other solution (I do not like this kind of solutions though) would be assigning a team directly to an activity while creating it.
                        // If so, then the user should be able to choose whether to:
                        // First, create a new team and then add member to it by choosing from registered members or by creating new members.
                        // Second, to choose an already registered team that has members.

                        // Note: if the team assigned to an activity does not have any members,
                        // we would get some exceptions popping later while doing the calculations.

                        proceed = false;
                    } catch (ActivityAlreadyRegisteredException | ActivityIsNullException e) {
                        Print.println(e + Print.newline);
                        proceed = false;
                    }
                    break;
                case 3:
                    editTask();
                    proceed = false;
                    break;
                case 4:
                    taskRemover();
                    proceed = false;
                    break;
                case 5:
                    taskAssigner();
                    proceed = false;
                    break;
                case 6:
                    taskTimeSetter();
                    proceed = false;
                    break;

                case 7:// Back to the previous menu
                    proceed = true;
                    break;

                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        } while (!proceed);
    }

    public static void taskTimeSetter() {


        // TODO logic for workOnActivty method
    }

    public static boolean taskAssigner() {
        Activity task;
        try {
            task = Print.readActivity();
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.newline);
            return false;
        }
        Team team;
        try {
            team = Print.readTeam();
        } catch (TeamIsNullException e) {
            Print.println(e + Print.newline);
            return false;
        }
        try {
            team.addActivity(task);
            task.setTeam(team); //TODO: not sure about this line. Do we still need a team for an activity(since teams are saved separately in their list)?
        } catch (ActivityAlreadyRegisteredException | ActivityIsNullException e) {
            Print.println(e + Print.newline);
            return false;
        }
        return false;
    }

    public static boolean taskRemover() {
        Activity task;
        try {
            task = Print.readActivity();
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.newline);
            return false;
        }
        try {
            project.getSchedule().removeActivity(task);
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.newline);
        }
        return false;
    }

    public static boolean editTask() {
        Activity activity = null;
        try {
            activity = Print.readActivity();
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.newline);
            return false;
        }
        do {
            switch (Print.printEditTaskMenu()) {

                case 1:
                    activity.setName(myScanner.readLine("Enter the new name: "));
                    proceed = false;
                    break;
                case 2:
                    activity.setEndWeek(myScanner.readInt("Enter the new end week: "));
                    proceed = false;
                    break;
                case 3:
                    activity.setEndYear(myScanner.readInt("Enter the new end year: "));
                    proceed = false;
                    break;
                case 4:
                    proceed = true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        } while (!proceed);
        return false;
    }


    private static void riskManager() {
        do {
            switch (Print.printRiskMenu()) {

                case 1:
                    if (project.getRiskMatrix().getRisks().isEmpty()) {
                        System.out.println("No registered risks!" + Print.newline);
                    } else {
                        System.out.println(project.getRiskMatrix().toStringText());
                    }
                    proceed = false;
                    break;
                case 2:
                    try {
                        project.getRiskMatrix().addRisk(Print.createRisk());
                        proceed = false;
                    } catch (RiskIsNullException | RiskAlreadyRegisteredException e) {
                        Print.println(e + Print.newline);
                        proceed = false;
                    }
                    break;
                case 3:
                    try {
                        project.getRiskMatrix().removeRisk(Print.readRisk());
                        proceed = false;
                    } catch (RiskIsNullException e) {
                        Print.println(e + Print.newline);
                        proceed = false;
                    }
                    break;
                case 4: //Back to previous menu
                    proceed = true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        } while (!proceed);
    }


    private static void teamMenu() {
        do {
            switch (Print.printTeamMenu()) {
                case 1:
                    if (project.getTeam().getMembers().isEmpty()) {
                        System.out.println("No registered members!" + Print.newline);
                    } else {
                        System.out.println(project.getTeam().toString() + Print.newline);

                    }
                    proceed = false;
                    break;
                case 2:
                    if (project.getTeams().isEmpty()) {
                        System.out.println("No registered teams!" + Print.newline);
                    } else {
                        for (Team team : project.getTeams()) {
                            System.out.println(team + Print.newline);
                        }
                    }
                    proceed = false;
                    break;
                case 3:
                    try {
                        project.getTeam().addMember(Print.createMember());
                        proceed = false;
                    } catch (MemberIsNullException e) {
                        e.printStackTrace();
                        proceed = false;
                    } catch (MemberAlreadyRegisteredException e) {
                        e.printStackTrace();
                        proceed = false;
                    }
                    break;
                case 4:
                    editMember();
                    proceed = false;
                    break;
                case 5:

                    try {
                        project.getTeam().removeMember(Print.readMember());
                    } catch (MemberIsNullException e) {
                        System.out.println(e + Print.newline);
                        proceed = false;
                    }
                    proceed = false;
                    break;
                case 6:
                    try {
                        project.addTeam(Print.createTeam());
                        proceed = false;
                    } catch (TeamAlreadyRegisteredException e) {
                        e.printStackTrace();
                        proceed = false;
                    } catch (TeamIsNullException e) {
                        e.printStackTrace();
                        proceed = false;
                    }
                    break;
                case 7:
                    editTeam();
                    proceed = false;
                    break;
                case 8://Back to previous menu
                    proceed = true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        } while (!proceed);
    }

    private static boolean editMember() {
        Member member;
        try {
            member = Print.readMember();
        } catch (MemberIsNullException e) {
            System.out.println(e + Print.newline);
            return false;
        }
        do {
            switch (Print.printEditMemberMenu()) {
                case 1:
                    member.setName(myScanner.readLine("Enter the members new name: "));
                    proceed = false;
                    break;
                case 2:
                    member.setSALARY_PER_HOUR(myScanner.readDouble("Enter the members new salary: "));
                    proceed = false;
                    break;
                case 3:
                    proceed = true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        } while (!proceed);
        return false;
    }

    private static boolean editTeam() {
        Team team;
        try {
            team = Print.readTeam();
        } catch (TeamIsNullException e) {
            Print.println(e + Print.newline);
            return false;
        }
        do {
            switch (Print.printEditSubTeamMenu()) {

                case 1:
                    team.setName(myScanner.readLine("Enter the teams new name: "));
                    proceed = false;
                    break;
                case 2:
                    try {
                        team.addMember(Print.readMember());
                        proceed = false;
                    } catch (MemberIsNullException | MemberAlreadyRegisteredException e) {
                        Print.println(e + Print.newline);
                        proceed = false;
                    }
                    break;
                case 3:
                    try {
                        team.removeMember(Print.readMember());
                        proceed = false;
                    } catch (MemberIsNullException e) {
                        Print.println(e + Print.newline);
                        proceed = false;
                    }
                    break;
                case 4:
                    proceed = true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        } while (!proceed);
        return false;
    }


    public static Member retrieveMember(String name) {
        for (Member member : project.getTeam().getMembers()) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    public static Team retrieveTeam(String name) {
        for (Team team : project.getTeams()) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }

    public static Activity retrieveActivity(String name) {
        for (Activity activity : project.getSchedule().getActivities()) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public static Risk retrieveRisk(String name) {
        for (Risk risk : project.getRiskMatrix().getRisks()) {
            if (risk.getRiskName().equals(name)) {
                return risk;
            }
        }
        return null;
    }


    private static void projectMenu() {
        do {
            switch (Print.printProjectMenu()) {
                case 1:
                    Print.printProject(project);
                    proceed = false;
                    break;
                case 2:
                    editProject();
                    proceed = false;
                    break;
                case 3:
                    proceed = true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        } while (!proceed);
    }

    public static void editProject() {

        do {
            switch (Print.printEditProjectMenu()) {

                case 1:
                    project.setName(Print.enterName());
                    proceed = false;
                    break;
                case 2:
                    project.getSchedule().setEnd(Print.ender());
                    proceed = false;
                    break;
                case 3:
                    proceed = true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        } while (!proceed);
    }

    public static void setProject(Project pro) {
        project = pro;
    }

    public static Project getProject() {
        return ConsoleProgram.project;
    }

    private static int createProject() {
        setProject(Print.createProject());
        return PROJECT_CREATED;
    }


}
