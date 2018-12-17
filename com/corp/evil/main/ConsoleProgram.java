public class ConsoleProgram {

    private final static int NO_PROJECT = -1;

    private final static int PROJECT = 1;
    private final static int MAIN = 2;
    private final static int SUCCESS = 42;

    private final static int LOAD = 1;
    private final static int NEW = 2;

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
                    System.out.println(project);
                    break;
                case MAIN:
                    //????

                    break;
                default:
                    break;
            }
        } while (proceed);

        return SUCCESS;
    }

    private static int loadOrNewProject() {
        switch (Print.printStartMenu()) {
            case LOAD:
                Print.loadProject();
                break;
            case NEW:
                Print.startProject();
                break;
            default:
                proceed = false;
                return NO_PROJECT;
        }
        return 0;
    }

    public static void setProject(Project pro) {
        project = pro;
    }
}




/*
        do {

            int choice = Print.printEditing();

            switch (choice) {

                case 1: //edit project name
                    String name = Print.printEditingName();
                    project.setName(name);
                    break;

                case 2:
                    //print all teams
                    printAllTeams();
                    // EDITING TEAM
                    do {
                        // print team Team.formattable();
                        choice = Print.printEditingTeam();

                        switch (choice) {

                            case 1:
                                project.addMember(Print.addMember());
                            case 2:
                                project.removeMember(Print.chooseMember());
                                break;
                            case 3:
                                project.getSchedule().addActivity(Print.createActivity());
                                break;
                            case 4:
                                project.getSchedule().removeActivity(Print.removeActivity());
                                break;
                            case 5:
                                project.workOnActivity();
                                break;

                        }
                        while (choice != 6)

                            // EDITING RISKMATRIX
                            case 3:
                        //print riskmatrix

                        choice = Print.printEditingRiskMatrix();

                        switch (choice) {

                            case 1:
                                //add risk - print how
                                project.addRisk();
                                break;
                            case 2:
                                //remove risk - print how
                                project.removeRisk();
                                break;

                            break;

                            // EDITING SCHEDULE
                            case 4:

                                choice = Print.printEditingSchedule();
                                switch (choice)
                            case 1:
                                //print how to add
                                project.addActivity();
                                break;
                            case 2:
                                //print how to remove
                                project.removeActivity();
                                break;


                            break;
                        }
                        while (choice != 6) ;

                    }
            }
        }*/