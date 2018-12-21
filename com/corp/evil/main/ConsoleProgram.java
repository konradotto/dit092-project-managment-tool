public class ConsoleProgram {

    // constants
    private static final int NO_PROJECT = -1;

    private static final int PROJECT = 1;
    private static final int MAIN = 2;
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



    private static Project project;
    private static boolean proceed = true;

    public static void main(String[] args) {
        run();
    }

    private static int run() {
        // set entry point for the console program
        int position = PROJECT;

        do {
            switch (position) {
                case PROJECT:       // print start menu and choose whether to load or create a new project
                    position = loadOrNewProject();
                    break;
                case MAIN:
                    primaryMenu();

                    break;
                default:
                    break;
            }
        } while (proceed);

        return SUCCESS;
    }

    public static void primaryMenu() {
        switch (Print.printPrimaryMeny()) {
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
                project.getBudget();
                break;
            case PRIMARY_SAVE_EXIT:
                // TODO save and exit method
                // TODO loop
                break;
            default:
                break;
        }
    }

    public static void taskManager() {
        switch (Print.printTasksMenu()) {
            case 1:
                project.getSchedule().toString();
                break;
            case 2:
                try {
                    project.getSchedule().addActivity(Print.createActivity());
                } catch (ActivityAlreadyRegisteredException e) {
                    e.printStackTrace();
                } catch (ActivityIsNullException e) {
                    e.printStackTrace();
                }
                break;
            case 3://TODO Edit task menu and method
                break;
            case 4:
                taskRemover();
                break;
            case 5:
                taskAssigner();
                break;
            case 6:
                taskTimeSetter();
                //TODO loop
        }
    }

    public static void taskTimeSetter() {
        Activity task = Print.readActivity();

        // TODO logic for workOnActivty method
    }

    public static void taskAssigner() {
        Activity task = Print.readActivity();
        Team team = Print.readTeam();

        try {
            team.addActivity(task);
            task.setTeam(team);
        } catch (ActivityAlreadyRegisteredException e) {
            e.printStackTrace();
        } catch (ActivityIsNullException e) {
            e.printStackTrace();
        }
    }

    public static void taskRemover() {
        Activity task = Print.readActivity();
        try {
            project.getSchedule().removeActivity(task);
        } catch (ActivityIsNullException e) {
            e.printStackTrace();
        }

    }

    private static void riskManager() {
        int x = Print.printRiskMenu();
        while (!((x == 1) || (x == 2) || (x == 3))) {
            x = Print.printRiskMenu();
        }
        if (x == 1) {
            project.getRiskMatrix().toString();
        } else if (x == 2) {
            Risk risk = Print.createRisk();
            try {
                project.getRiskMatrix().addRisk(risk);
            } catch (RiskIsNullException e) {
                e.printStackTrace();
            } catch (RiskAlreadyRegisteredException e) {
                e.printStackTrace();
            }
        } else {
            Risk risk = Print.readRisk();
            try {
                project.getRiskMatrix().removeRisk(risk);
            } catch (RiskIsNullException e) {
                e.printStackTrace();
            }
        }
        //TODO maybe a do-while instead of a while? + loop
    }

    private static void teamMenu() {
        switch (Print.printTeamMenu()) {
            case 1:
                System.out.println(project.getTeam().toString() + Print.newline);
                break;
            case 2:
                for (Team team : project.getTeams()) {
                    System.out.println(team.toString() + Print.newline);
                }
                break;
            case 3:
                Member member = Print.createMember();
                try {
                    project.getTeam().addMember(member);
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
                Member member1 = Print.readMember();
                project.removeMember(member1);
                break;
            case 6:
                Print.createTeam();
                break;
            case 7:
                editTeam();
                break;
            default:
                break;
            //TODO loop
        }
    }

    private static void editMember() {
        int x = Print.printEditMemberMenu();
        while (!(x == 1 || x == 2)) {
            x = Print.printEditMemberMenu();
        }
        Member member = Print.readMember();
        if (x == 1) {

            member.setName(myScanner.readLine("Enter the members new name: "));
        } else {
            member.setSALARY_PER_HOUR(myScanner.readDouble("Enter the members new salary: "));
        }
    }

    private static void editTeam() {
        int x = Print.printEditSubTeamMenu();
        while (!(x == 1 || x == 2 || x == 3)) {
            x = Print.printEditSubTeamMenu();
        }
        Team team = Print.readTeam();
        if (x == 1) {
            team.setName(myScanner.readLine("Enter the teams new name: "));
        } else if (x == 2) {
            Member member = Print.readMember();
            try {
                team.addMember(member);
            } catch (MemberIsNullException e) {
                e.printStackTrace();
            } catch (MemberAlreadyRegisteredException e) {
                e.printStackTrace();
            }
        } else {
            Member member = Print.readMember();
            try {
                team.removeMember(member);
            } catch (MemberIsNullException e) {
                e.printStackTrace();
            }
        }
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

    private static int loadOrNewProject() {
        switch (Print.printStartMenu()) {
            case LOAD:
                Print.loadProject();
                break;
            case NEW:
                createProject();
                break;
            default:
                proceed = false;
                return NO_PROJECT;
        }
        return MAIN;
    }

    private static void projectMenu() {
        int x;
        do {
            x = Print.printProjectMenu();
        } while (!(x == PRINT_PROJET || x == EDIT_PROJECT));

        if (x == PRINT_PROJET) {
            Print.printProject(project);
        } else if (x == EDIT_PROJECT) {
            editProject();
        }
    }

    public static void editProject() {
        int x = Print.printEditProjectMenu();
        while (!(x == 1 || x == 2)) {
            x = Print.printEditProjectMenu();
        }
        if (x == 1) {
            project.setName(Print.enterName());
        } else {
            project.getSchedule().setEnd(Print.ender());
        }
    }

    public static void setProject(Project pro) {
        project = pro;
    }

    public static Project getProject() {
        return ConsoleProgram.project;
    }

    public static void createProject() {
        setProject(Print.createProject());
    }


}
