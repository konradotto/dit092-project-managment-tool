/**
 * Class responsible to keep track of the current state (finite state machine of menus),
 * input-output and hosting the project.
 */
public class ConsoleProgram {

    // entry menu states
    private static final int PROJECT = 1;
    private static final int MAIN = 2;
    private static final int END = 3;

    private static final int SUCCESS = 42;
    private static final int LOAD = 1;
    private static final int NEW = 2;

    // project menu options
    private static final int PRINT_PROJECT = 1;
    private static final int EDIT_PROJECT = 2;
    private static final int LEAVE_PROJECT_MENU = 3;
    private static final int EDIT_PROJECT_NAME = 1;
    private static final int EDIT_PROJECT_END = 2;

    // primary/main menu options
    private static final int PRIMARY_PROJECT = 1;
    private static final int PRIMARY_TEAM = 2;
    private static final int PRIMARY_TASK = 3;
    private static final int PRIMARY_RISK = 4;
    private static final int PRIMARY_BUDGET = 5;
    private static final int PRIMARY_SAVE_EXIT = 6;

    // schedule menu options
    private static final int TASKS_PRINT_ALL = 1;
    private static final int TASK_ADD = 2;
    private static final int TASK_EDIT = 3;
    private static final int LEAVE_TASK_MANAGER = 4;

    // task editing menu options
    private static final int TASK_REMOVE = 1;
    private static final int EDIT_TASK_NAME = 2;
    private static final int EDIT_TASK_END_WEEK = 3;
    private static final int EDIT_TASK_END_YEAR = 4;
    private static final int TASK_ASSIGN_TEAM = 5;
    private static final int TASK_UPDATE_TIME_SPENT = 6;
    private static final int LEAVE_TASK_MENU = 7;

    // project editing menu options
    private static final int PROJECT_PRINT_MEMBERS = 1;
    private static final int PROJECT_PRINT_TEAMS = 2;
    private static final int PROJECT_ADD_MEMBER = 3;
    private static final int PROJECT_EDIT_MEMBER = 4;
    private static final int PROJECT_REMOVE_MEMBER = 5;
    private static final int PROJECT_ADD_TEAM = 6;
    private static final int TEAM_EDIT = 7;
    private static final int LEAVE_TEAM_MANAGER = 8;

    // member edit menu options
    private static final int EDIT_MEMBER_NAME = 1;
    private static final int EDIT_MEMBER_SALARY = 2;
    private static final int PRINT_MEMBER_HOURS = 3;
    private static final int LEAVE_MEMBER_MENU = 4;

    // edit team menu options
    private static final int TEAM_CHANGE_NAME = 1;
    private static final int TEAM_ADD_MEMBER = 2;
    private static final int TEAM_REMOVE_MEMBER = 3;
    private static final int LEAVE_TEAM_MENU = 4;

    // risk menu options
    private static final int PRINT_RISK_MATRIX = 1;
    private static final int ADD_RISK = 2;
    private static final int REMOVE_RISK = 3;
    private static final int LEAVE_RISK_MANAGER = 4;

    // budget menu options
    private static final int BUDGET_PRINT_FULL = 1;
    private static final int BUDGET_EARNED_VALUE = 2;
    private static final int BUDGET_COST_VARIANCE = 3;
    private static final int BUDGET_SCHEDULE_VARIANCE = 4;
    private static final int BUDGET_CHANGE_DATE = 5;
    private static final int LEAVE_BUDGET_MENU = 6;

    // member selection options
    private static final int MEMBER_BY_NAME = 1;
    private static final int MEMBER_FROM_LIST = 2;

    // members
    private static Project project;

    /**
     * Routine to run the ConsoleProgram.
     * Start by choosing a project.
     * Afterwards the main menu is entered and looped.
     *
     * @return value depending on whether the program has been completed without complications
     */
    public static int run() {
        boolean proceed = true;
        // set entry point for the console program
        int position = PROJECT;
        do switch (position) {
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
        } while (proceed);

        return SUCCESS;     // return success after finishing the program
    }

    /**
     * Routine to load an existing project or create a new one.
     *
     * @return next point in the program to continue from
     */
    private static int loadOrNewProject() {
        int next = PROJECT;                 // default next action if nothing changes
        switch (Print.printStartMenu()) {
            case LOAD:
                next = loadProject();       // set next to MAIN if and only if PROJECT_LOADED
                break;
            case NEW:
                createProject();            // after this we always want ot execute MAIN
                next = MAIN;
                break;
            case END:
                next = END;
                break;
            default:
                Print.defaultMonologue();
                break;
        }
        return next;
    }

    /**
     * Routine displaying the primary Menu of the project-management-tool.
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
                projectBudgetManager();
                break;
            case PRIMARY_SAVE_EXIT:
                next = END;
                break;
            default:
                Print.defaultMonologue();
                break;
        }
        return next;
    }

    /**
     * Routine for closing the Console Program.
     * Prints a Goodbye-Message and saves the project.
     */
    private static void endConsoleProgram() {
        boolean projectUsed = false;
        if (project != null) {      // make sure project exists (it doesn't if no load or creteProject() was done)
            project.saveProject();
            projectUsed = true;
        }
        Print.exitProgram(projectUsed);
    }

    /**
     * Routine for running the project-menu.
     * Will offer to print or edit a project or leave until leaving is chosen.
     */
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
            case LEAVE_TASK_MANAGER:
                leaveMenu = true;
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leaveMenu);
    }

    public static boolean taskTimeSetter(Activity task) {
        Member member = chooseMember(task.getTeam());

        if (member == null) {
            Print.println("No one is working on this activity yet." + Print.LS);
            return false;
        }

        int timeSpent = myScanner.readInt("Enter the amount of time that " + member.getName() + " has spent on " + task.getName() + ':');
        int timeScheduled = myScanner.readInt("Enter the amount of scheduled time that " + member.getName() + " has spent on " + task.getName() + ':');

        task.work(member, timeSpent, timeScheduled);
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

    public static void editTask() {
        Activity activity;
        try {
            activity = Print.readActivity();
        } catch (ActivityIsNullException e) {
            Print.println(e.getMessage() + Print.LS);
            Print.println("Returning to last menu...");
            return;
        }

        boolean leave = false;
        do switch (Print.printEditTaskMenu()) {
            case TASK_REMOVE:
                project.removeActivity(activity);
                leave = true;
                break;
            case EDIT_TASK_NAME:
                activity.setName(myScanner.readLine("Enter the new name: "));
                project.onChange();
                break;
            case EDIT_TASK_END_WEEK:
                activity.setEndWeek(myScanner.readInt("Enter the new end week: "), project.getSchedule().getTimePeriod());
                project.onChange();
                break;
            case EDIT_TASK_END_YEAR:
                activity.setEndYear(myScanner.readInt("Enter the new end year: "), project.getSchedule().getTimePeriod());
                project.onChange();
                break;
            case TASK_ASSIGN_TEAM:
                try {
                    project.assignTask(activity, Print.readTeam());
                } catch (TeamIsNullException e) {
                    Print.println(e.getMessage() + Print.LS);
                }
                break;
            case TASK_UPDATE_TIME_SPENT:
                taskTimeSetter(activity);
                break;
            case LEAVE_TASK_MENU:
                leave = true;
                Print.println("Leaving the task editing menu...");
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leave);
    }


    private static void riskManager() {
        int choice;
        do switch (choice = Print.printRiskMenu()) {
            case PRINT_RISK_MATRIX:
                if (project.getRiskMatrix().getRisks().isEmpty()) {
                    Print.println("No registered risks!" + Print.LS);
                } else {
                    Print.println(project.getRiskMatrix().toStringText());
                }
                break;
            case ADD_RISK:
                try {
                    project.getRiskMatrix().addRisk(Print.createRisk());
                    project.onChange();
                } catch (RiskIsNullException | RiskAlreadyRegisteredException e) {
                    Print.println(e.getMessage() + Print.LS);
                }
                break;
            case REMOVE_RISK:
                try {
                    project.getRiskMatrix().removeRisk(Print.readRisk());
                    project.onChange();
                } catch (RiskIsNullException e) {
                    Print.println(e.getMessage() + Print.LS);
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

    private static void projectBudgetManager() {
        int choice;
        do switch (choice = Print.printProjectBudgetMenu()) {
            case BUDGET_PRINT_FULL:
                Print.println(project.getBudgetString());
                break;
            case BUDGET_EARNED_VALUE:
                Print.println(project.getEarnedValueString());
                break;
            case BUDGET_COST_VARIANCE:
                Print.println(project.getCostVarianceString());
                break;
            case BUDGET_SCHEDULE_VARIANCE:
                Print.println(project.getScheduleVarianceString());
                break;
            case BUDGET_CHANGE_DATE:
                try {
                    project.setDate(Print.readYearWeek(), Print.readDayOfWeek());
                } catch (IllegalArgumentException e) {
                    Print.println(e.getMessage() + Print.LS);
                }
                break;
            case LEAVE_BUDGET_MENU:
                Print.println("Leaving the budget manager...");
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (choice != LEAVE_BUDGET_MENU);
    }


    //TODO: this needs some clean-up
    private static void teamMenu() {
        boolean leave = false;
        do switch (Print.printTeamMenu()) {
            case PROJECT_PRINT_MEMBERS:
                Print.println(project.getTeamString());
                break;
            case PROJECT_PRINT_TEAMS:
                Print.println(project.getTeamsString());
                break;
            case PROJECT_ADD_MEMBER:
                try {
                    project.getTeam().addMember(Print.createMember());
                } catch (MemberIsNullException | MemberAlreadyRegisteredException e) {
                    Print.println(e.getMessage() + Print.LS);
                }
                break;
            case PROJECT_EDIT_MEMBER:
                editMember(project.getTeam());
                break;
            case PROJECT_REMOVE_MEMBER:
                Member member = chooseMember(project.getTeam());
                if (member == null) {
                    Print.println("No such member!");
                    break;
                }
                project.removeMember(member);
                project.memberNameChanger(member, "(Removed) " +member.getName());
                break;
            case PROJECT_ADD_TEAM:
                try {
                    project.addTeam(Print.createTeam());
                } catch (TeamAlreadyRegisteredException | TeamIsNullException e) {
                    Print.println(e.getMessage() + Print.LS);
                }
                break;
            case TEAM_EDIT:
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
                    member.setSalaryPerHour(salary);
                    project.memberSalaryChanger(member,salary);
                    break;
                case PRINT_MEMBER_HOURS:
                    Print.println(member.toString());
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
            Print.println(e.getMessage() + Print.LS);
            return false;
        }

        boolean leave = false;
        do switch (Print.printEditSubTeamMenu()) {

            case TEAM_CHANGE_NAME:
                team.setName(myScanner.readLine("Enter the teams new name: "));
                break;
            case TEAM_ADD_MEMBER:
                try {
                    Member member = chooseMember(project.getTeam());
                    if (member == null) {
                        Print.println("Member is null! Returning to team menu...");
                        break;
                    }
                    team.addMember(member);
                } catch (MemberIsNullException | MemberAlreadyRegisteredException e) {
                    Print.println(e.getMessage() + Print.LS);
                }
                break;
            case TEAM_REMOVE_MEMBER:
                try {
                    Member member = chooseMember(team);
                    if (member == null) {
                        throw new MemberIsNullException("No member selected to remove from the team!");
                    }
                    team.removeMember(member);
                } catch (MemberIsNullException e) {
                    Print.println(e.getMessage() + Print.LS);
                }
                break;
            case LEAVE_TEAM_MENU:
                leave = true;
                break;
            default:
                Print.defaultMonologue();
                break;
        } while (!leave);

        return false;       // TODO: this is always returning false...
    }


    //TODO: do we really need the projectEditingMenu? Changing the start date or name from the projectMenu should be good enough...

    /**
     * Routine to edit a project's name or end date.
     */
    public static void editProject() {
        boolean leave = false;
        do switch (Print.printEditProjectMenu()) {
            case EDIT_PROJECT_NAME:
                project.setName(Print.enterName());
                break;
            case EDIT_PROJECT_END:
                project.setEndWeek(Print.pickEndWeek("project"));
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


    /**
     * Routine trying to load a project and set the project member here.
     *
     * @return point of continuation for the entry-menu (either continue with main or rerun the project-selection)
     */
    private static int loadProject() {
        if (Print.loadProject() != Print.PROJECT_LOADED) {
            Print.println("Loading the Project failed. Try again!" + Print.LS);
            return PROJECT;
        }
        return MAIN;
    }

    private static void createProject() {
        project = Print.createProject();
    }

    public static void setProject(Project pro) {
        project = pro;
    }

    public static Project getProject() {
        return ConsoleProgram.project;
    }
}
