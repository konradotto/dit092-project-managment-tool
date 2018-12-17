public class ConsoleProgram {

    private final static int SUCCESS = 42;

    private final static int LOAD = 1;
    private final static int NEW = 2;

    public static void main(String[] args) {
        run();
    }

    private static int run() {
        // print start menu and choose whether to load or create a new project
        Project project = loadOrNewProject(Print.printStartMenu());


        return SUCCESS;
    }

    private static Project loadOrNewProject(int choice) {
        Project project = null;

        switch (Print.printStartMenu()) {
            case LOAD:

                break;
            case NEW:
                // load default project
                //toString oject print
                break;
            default:
                // decide on default behaviour
        }

        return project;
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