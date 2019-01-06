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


    private static final int LEAVE_RISK_MANAGER = 4;

    private static final int MEMBER_BY_NAME = 1;
    private static final int MEMBER_FROM_LIST = 2;

    // result constants
    private static final int PROJECT_CREATED = 1;

    // members
    private static Project project;
    //private static boolean proceed = true;

    /**
     * Function to run the ConsoleProgram.
     * Start by choosing a project.
     * Afterwards the main menu is entered and looped.
     *
     * @return value depending on whether the program has been completed without complications
     */
    public static int run() {
        boolean proceed = true;
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
                    Print.println("Loading the Project failed. Try again!" + Print.LS);
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
                Print.println("Choose a valid option!" + Print.LS);
                next = PROJECT;
                break;
        }

        return next;
    }

    /**
     * Function displaying the primary Menu of our application
     *
     * @return
     */
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
                break;
            case PRIMARY_RISK:
                riskManager();
                break;
            case PRIMARY_BUDGET:
                //TODO: Is this all we want for the budget?
                Print.println(project.getBudget());
                break;
            case PRIMARY_SAVE_EXIT:
                next = END;
                break;
            default:
                System.out.println("Choose a valid option!\n");
                next = primaryMenu();
                break;
        }
        return next;
    }

    /**
     * Function closing the Console Program.
     * Prints a Goodbye-Message and saves the project.     *
     */
    private static void endConsoleProgram() {
        boolean projectUsed = false;
        if (project != null) {      // make sure project exists
            project.saveProject();
            projectUsed = true;
        }
        Print.exitProgram(projectUsed);
    }


    public static void taskManager() {
        boolean leaveMenu = false;
        do {
            switch (Print.printTasksMenu()) {
                case 1:
                    Print.println(project.getSchedule().toString());
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

                    } catch (ActivityAlreadyRegisteredException | ActivityIsNullException e) {
                        Print.println(e + Print.LS);
                    }
                    break;
                case 3:
                    editTask();
                    break;
                case 4:
                    taskRemover();
                    break;
                case 5:
                    taskAssigner();
                    break;
                case 6:
                    taskTimeSetter();
                    break;

                case 7:
                    leaveMenu = true;
                    break;

                default:
                    Print.println("Choose a valid option!\n");
                    break;
            }
        } while (!leaveMenu);
    }

    public static boolean taskTimeSetter() {
        Activity task;
        try {
            task = Print.readActivity();
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.LS);
            return false;
        }
        Member member = chooseMember();

        if (!task.getTeam().contains(member)) {
            Print.println("This member is not assigned to the chosen task!" + Print.LS);
            return false;
        } else {
            long timeSpent = myScanner.readLong("Enter the amount of time that " + member.getName() + " has spent on " + task.getName() + ':');
            long timeScheduled = myScanner.readLong("Enter the amount of scheduled time that " + member.getName() + " has spent on " + task.getName() + ':');

            task.getTeam().workOnActivity(member, task, timeSpent, timeScheduled);
        }
        return false;
    }

    public static Member chooseMember() {
        Member member;

        switch (Print.chooseMemberSelection()) {
            case MEMBER_BY_NAME:
                member = Print.chooseMemberByName(project);
                break;
            case MEMBER_FROM_LIST:
                member = Print.chooseMemberFromList(project);
                break;
            default:
                member = Print.chooseMemberFromList(project);
                break;
        }
        return member;
    }


    public static boolean taskAssigner() {
        Activity task;
        try {
            task = Print.readActivity();
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.LS);
            return false;
        }
        Team team;
        try {
            team = Print.readTeam();
        } catch (TeamIsNullException e) {
            Print.println(e + Print.LS);
            return false;
        }
        try {
            team.addActivity(task);
            task.setTeam(team);
            task.setCostOfWorkScheduled(task.scheduledCost());
        } catch (ActivityAlreadyRegisteredException | ActivityIsNullException e) {
            Print.println(e + Print.LS);
            return false;
        }
        return false;
    }

    public static boolean taskRemover() {
        Activity task;
        try {
            task = Print.readActivity();
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.LS);
            return false;
        }
        try {
            project.getSchedule().removeActivity(task);
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.LS);
        }
        return false;
    }

    public static boolean editTask() {
        Activity activity = null;
        try {
            activity = Print.readActivity();
        } catch (ActivityIsNullException e) {
            Print.println(e + Print.LS);
            return false;
        }

        boolean leave = false;
        do switch (Print.printEditTaskMenu()) {

            case 1:
                activity.setName(myScanner.readLine("Enter the new name: "));
                break;
            case 2:
                activity.setEndWeek(myScanner.readInt("Enter the new end week: "));
                break;
            case 3:
                activity.setEndYear(myScanner.readInt("Enter the new end year: "));
                break;
            case 4:
                leave = true;
                Print.println("Leaving the task editing menu...");
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leave);

        return false;       //TODO: always returning false
    }

    //TODO: clean up exceptions
    private static void riskManager() {
        int choice;
        do switch (choice = Print.printRiskMenu()) {
            case 1:
                if (project.getRiskMatrix().getRisks().isEmpty()) {
                    System.out.println("No registered risks!" + Print.LS);
                } else {
                    System.out.println(project.getRiskMatrix().toStringText());
                }
                break;
            case 2:
                try {
                    project.getRiskMatrix().addRisk(Print.createRisk());
                } catch (RiskIsNullException | RiskAlreadyRegisteredException e) {
                    Print.println(e + Print.LS);
                }
                break;
            case 3:
                try {
                    project.getRiskMatrix().removeRisk(Print.readRisk());
                } catch (RiskIsNullException e) {
                    Print.println(e + Print.LS);
                }
                break;
            case LEAVE_RISK_MANAGER:
                Print.println("Leaving the risk manager...");
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (choice != LEAVE_RISK_MANAGER);
    }


    //TODO: this needs some clean-up
    private static void teamMenu() {
        boolean leave = false;
        do switch (Print.printTeamMenu()) {
            case 1:
                if (project.getTeam().getMembers().isEmpty()) {
                    System.out.println("No registered members!" + Print.LS);
                } else {
                    System.out.println(project.getTeam().toString() + Print.LS);

                }
                break;
            case 2:
                if (project.getTeams().isEmpty()) {
                    System.out.println("No registered teams!" + Print.LS);
                } else {
                    for (Team team : project.getTeams()) {
                        System.out.println(team + Print.LS);
                    }
                }
                break;
            case 3:
                try {
                    project.getTeam().addMember(Print.createMember());
                } catch (MemberIsNullException e) {
                    e.printStackTrace();
                } catch (MemberAlreadyRegisteredException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                editMember();
                break;
            case 5:

                try {
                    project.getTeam().removeMember(chooseMember());
                } catch (MemberIsNullException e) {
                    Print.println(e + Print.LS);    //TODO: use error message
                }
                break;
            case 6:
                try {
                    project.addTeam(Print.createTeam());
                } catch (TeamAlreadyRegisteredException e) {
                    e.printStackTrace();
                } catch (TeamIsNullException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                editTeam();
                break;
            case 8://Back to previous menu
                leave = true;
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leave);
    }

    private static boolean editMember() {

        Member member = chooseMember();
        if (member == null) {
            return false;
        }

        boolean leave = false;
        do {
            switch (Print.printEditMemberMenu()) {
                case 1:
                    member.setName(myScanner.readLine("Enter the members new name: "));
                    break;
                case 2:
                    member.setSALARY_PER_HOUR(myScanner.readDouble("Enter the members new salary: "));
                    break;
                case 3:
                    leave = true;
                    break;
                default:
                    Print.defaultMonologue();
                    break;
            }
        } while (!leave);
        return false;       //TODO: allow returning true
    }

    private static boolean editTeam() {
        Team team;
        try {
            team = Print.readTeam();
        } catch (TeamIsNullException e) {
            Print.println(e + Print.LS);
            return false;
        }

        boolean leave = false;
        do switch (Print.printEditSubTeamMenu()) {

            case 1:
                team.setName(myScanner.readLine("Enter the teams new name: "));
                break;
            case 2:
                try {
                    team.addMember(chooseMember());
                } catch (MemberIsNullException | MemberAlreadyRegisteredException e) {
                    Print.println(e + Print.LS);
                }
                break;
            case 3:
                try {
                    team.removeMember(chooseMember());
                } catch (MemberIsNullException e) {
                    Print.println(e + Print.LS);
                }
                break;
            case 4:
                leave = true;
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leave);

        return false;       // TODO: this is always returning false...
    }


    //TODO: move these to Project-class

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
        boolean leave = false;
        do switch (Print.printProjectMenu()) {
            case 1:
                Print.println(project.toString());
                break;
            case 2:
                editProject();
                break;
            case 3:
                Print.println("Leaving the general project menu...");
                leave = true;
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leave);
    }


    //TODO: do we really need the projectEditingMenu? Changing the start date or name from the projectMenu should be good enough...

    /**
     * Function to edit a project's name or end date.
     */
    public static void editProject() {
        boolean leave = false;
        do switch (Print.printEditProjectMenu()) {
            case 1:
                project.setName(Print.enterName());
                break;
            case 2:
                project.getSchedule().setEnd(Print.ender());
                break;
            case 3:
                Print.println("Leaving the menu for project editing...");
                leave = true;
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leave);
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
