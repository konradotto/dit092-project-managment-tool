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

    private static final int PRINT_PROJECT = 1;
    private static final int EDIT_PROJECT = 2;
    private static final int LEAVE_PROJECT_MENU = 3;
    private static final int EDIT_PROJECT_NAME = 1;
    private static final int EDIT_PROJECT_END = 2;

    private static final int PRIMARY_PROJECT = 1;
    private static final int PRIMARY_TEAM = 2;
    private static final int PRIMARY_TASK = 3;
    private static final int PRIMARY_RISK = 4;
    private static final int PRIMARY_BUDGET = 5;
    private static final int PRIMARY_SAVE_EXIT = 6;

    private static final int PRINT_ALL_TASKS = 1;
    private static final int ADD_TASK = 2;
    private static final int EDIT_TASK_MENU = 3;
    private static final int REMOVE_TASK = 4;
    private static final int ASSIGN_TASK = 5;
    private static final int TASK_TIME_SETTER= 6;
    private static final int LEAVE_TASK_MANAGER= 7;

    private static final int EDIT_TASK_NAME= 1;
    private static final int EDIT_TASK_END_WEEK= 2;
    private static final int EDIT_TASK_END_YEAR= 3;
    private static final int LEAVE_TASK_MENU= 4;

    private static final int PRINT_PROJECT_MEMBERS = 1;
    private static final int PRINT_PROJECT_TEAMS = 2;
    private static final int ADD_MEMBER_PROJECT = 3;
    private static final int EDIT_MEMBER = 4;
    private static final int REMOVE_MEMBER = 5;
    private static final int ADD_TEAM = 6;
    private static final int EDIT_TEAM = 7;
    private static final int LEAVE_TEAM_MANAGER = 8;

    private static final int EDIT_MEMBER_NAME = 1;
    private static final int EDIT_MEMBER_SALARY = 2;
    private static final int LEAVE_MEMBER_MENU = 3;

    private static final int EDIT_TEAM_NAME = 1;
    private static final int ADD_MEMBER_TO_TEAM = 2;
    private static final int REMOVE_MEMBER_FROM_TEAM= 3;
    private static final int LEAVE_TEAM_MENU = 4;

    private static final int PRINT_RISK_MATRIX = 1;
    private static final int ADD_RISK = 2;
    private static final int REMOVE_RISK = 3;
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
                // TODO: Is this all we want for the budget?
                Print.println(project.getBudget());
                break;
            case PRIMARY_SAVE_EXIT:
                next = END;
                break;
            default:
                Print.println("Choose a valid option!\n");
                next = primaryMenu();
                break;
        }
        return next;
    }

    /**
     * Routine for closing the Console Program.
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
        do switch (Print.printTasksMenu()) {
            case TASKS_PRINT_ALL:
                Print.println(project.getSchedule().toString());
                break;
            case TASK_ADD:
                project.addActivity(Print.createActivity());
                break;
            case TASK_EDIT:
                editTask();
                break;
            case TASK_REMOVE:
                taskRemover();
                break;
            case TASK_ASSIGN_TEAM:
                taskAssigner();
                break;
            case TASK_UPDATE_TIME_SPENT:
                taskTimeSetter();
                break;
            case TASKS_LEAVE:
                leaveMenu = true;
                break;
            default:
                Print.println("Choose a valid option!" + Print.LS);
                break;
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
        Member member = chooseMember(task.getTeam());

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

    public static Member chooseMember(Team team) {
        Member member;

        switch (Print.chooseMemberSelection()) {
            case MEMBER_BY_NAME:
                member = Print.chooseMemberByName(team);
                break;
            case MEMBER_FROM_LIST:
                member = Print.chooseMemberFromList(team);
                break;
            default:
                member = Print.chooseMemberFromList(team);
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
        if (!team.getMembers().isEmpty()){
            try {
                team.addActivity(task);
                task.setTeam(team);
                task.setCostOfWorkScheduled(task.scheduledCost());
            } catch (ActivityAlreadyRegisteredException | ActivityIsNullException e) {
                Print.println(e + Print.LS);
                return false;
            }
        }
        else {
            Print.println("A task cannot be assigned to an empty team!");
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

            case EDIT_TASK_NAME:
                activity.setName(myScanner.readLine("Enter the new name: "));
                break;
            case EDIT_TASK_END_WEEK:
                activity.setEndWeek(myScanner.readInt("Enter the new end week: "));
                break;
            case EDIT_TASK_END_YEAR:
                activity.setEndYear(myScanner.readInt("Enter the new end year: "));
                break;
            case LEAVE_TASK_MENU:
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
            case PRINT_RISK_MATRIX:
                if (project.getRiskMatrix().getRisks().isEmpty()) {
                    System.out.println("No registered risks!" + Print.LS);
                } else {
                    System.out.println(project.getRiskMatrix().toStringText());
                }
                break;
            case ADD_RISK:
                try {
                    project.getRiskMatrix().addRisk(Print.createRisk());
                } catch (RiskIsNullException | RiskAlreadyRegisteredException e) {
                    Print.println(e + Print.LS);
                }
                break;
            case REMOVE_RISK:
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
            case PRINT_PROJECT_MEMBERS:
                if (project.getTeam().getMembers().isEmpty()) {
                    System.out.println("No registered members!" + Print.LS);
                } else {
                    System.out.println(project.getTeam().toString() + Print.LS);

                }
                break;
            case PRINT_PROJECT_TEAMS:
                if (project.getTeams().isEmpty()) {
                    System.out.println("No registered teams!" + Print.LS);
                } else {
                    for (Team team : project.getTeams()) {
                        System.out.println(team + Print.LS);
                    }
                }
                break;
            case ADD_MEMBER_PROJECT:
                try {
                    project.getTeam().addMember(Print.createMember());
                } catch (MemberIsNullException e) {
                    e.printStackTrace();
                } catch (MemberAlreadyRegisteredException e) {
                    e.printStackTrace();
                }
                break;
            case EDIT_PROJECT_MEMBER:
                editMember(project.getTeam());
                break;
            case REMOVE_PROJECT_MEMBER:
                project.removeMember(chooseMember(project.getTeam()));
                break;
            case ADD_TEAM:
                try {
                    project.addTeam(Print.createTeam());
                } catch (TeamAlreadyRegisteredException e) {
                    e.printStackTrace();
                } catch (TeamIsNullException e) {
                    e.printStackTrace();
                }
                break;
            case EDIT_TEAM:
                editTeam();
                break;
            case LEAVE_TEAM_MANAGER://Back to previous menu
                leave = true;
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leave);
    }

    private static boolean editMember(Team team) {

        Member member = chooseMember(team);
        if (member == null) {
            return false;
        }

        boolean leave = false;
        do {
            switch (Print.printEditMemberMenu()) {
                case EDIT_MEMBER_NAME:
                    String name = myScanner.readLine("Enter the members new name: ");
                    member.setName(name);
                    project.memberNameChanger(member,name);
                    break;
                case EDIT_MEMBER_SALARY:
                    double salary = myScanner.readDouble("Enter the members new salary: ");
                    member.setSALARY_PER_HOUR(salary);
                    project.memberSalaryChanger(member,salary);
                    break;
                case LEAVE_MEMBER_MENU:
                    Print.println("Leaving the edit member menu...");
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

            case SUBTEAM_CHANGE_NAME:
                team.setName(myScanner.readLine("Enter the teams new name: "));
                break;
            case SUBTEAM_ADD_MEMBER:
                try {
                    team.addMember(chooseMember(project.getTeam()));
                } catch (MemberIsNullException | MemberAlreadyRegisteredException e) {
                    Print.println(e + Print.LS);
                }
                break;
            case SUBTEAM_REMOVE_MEMBER:
                try {
                    team.removeMember(chooseMember(team));
                } catch (MemberIsNullException e) {
                    Print.println(e + Print.LS);
                }
                break;
            case SUBTEAM_LEAVE_MENU:
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
            case PRINT_PROJECT:
                Print.println(project.toString());
                break;
            case EDIT_PROJECT:
                editProject();
                break;
            case LEAVE_PROJECT_MENU:
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
            case EDIT_PROJECT_NAME:
                project.setName(Print.enterName());
                break;
            case EDIT_PROJECT_END:
                project.getSchedule().setEnd(Print.ender());
                break;
            case LEAVE_PROJECT_MENU:
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
