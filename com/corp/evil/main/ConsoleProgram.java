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
                    primaryMenu();
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

    public static void primaryMenu(){
        switch (Print.printPrimaryMeny()) {
            case 1:
                projectMenu();
                break;
            case 2:
                teamMenu();
                break;
            case 3:
                break;
            case 4:
                riskManager();
                break;
            case 5:
                project.getBudget();
                break;
            case 6:
                taskManager();
                break;
            default:
                break;
        }
    }

    public static void taskManager(){
        switch (Print.printTasksMenu()){
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
            case 3:
                break;
            case 4:
                taskRemover();
                break;
            case 5:
                taskAssigner();
                break;
            case 6:
                taskTimeSetter();
        }
    }

    public static void taskTimeSetter(){
        Activity task = retrieveActivity("Enter which activity you wish to set a time to: ");
    }

    public static void taskAssigner(){
        Activity task = retrieveActivity("Enter which activity you wish to assign to a team: ");
        Team team = retrieveTeam("Enter which team you want to assign the task to: ");

        try {
            team.addActivity(task);
        } catch (ActivityAlreadyRegisteredException e) {
            e.printStackTrace();
        } catch (ActivityIsNullException e) {
            e.printStackTrace();
        }
    }

    public static void taskRemover(){
        Activity task  = retrieveActivity(myScanner.readLine("Enter the task you wish to remove: "));
        try {
            project.getSchedule().removeActivity(task);
        } catch (ActivityIsNullException e) {
            e.printStackTrace();
        }

    }

    public static Activity retrieveActivity(String name){
        for (Activity activity : project.getSchedule().getActivities()) {
            if (activity.getName().equals(name)) {
                return activity;
            }
        }
        return null;
    }



    private static void riskManager(){
        int x = Print.printRiskMenu();
        while (!(x == 1 || x == 2)){
            x = Print.printRiskMenu();
        }
        if (x == 1){
            project.getRiskMatrix().toString();
        }
        else if(x == 2){
            Risk risk = Print.createRisk();
            try {
                project.getRiskMatrix().addRisk(risk);
            } catch (RiskIsNullException e) {
                e.printStackTrace();
            } catch (RiskAlreadyRegisteredException e) {
                e.printStackTrace();
            }
        } else {
            Risk risk = retrieveRisk(myScanner.readLine("Enter the risk you want to remove"));
            try {
                project.getRiskMatrix().removeRisk(risk);
            } catch (RiskIsNullException e) {
                e.printStackTrace();
            }
        }
    }

    public static Risk retrieveRisk(String name) {
        for (Risk risk : project.getRiskMatrix().getRisks()) {
            if (risk.getRiskName().equals(name)) {
                return risk;
            }
        }
        return null;
    }



    private static void teamMenu(){
        switch (Print.printTeamMenu()) {
            case 1:
                System.out.println(project.getTeam().toString() + Print.newline);
                break;
            case 2:
                for(Team team : project.getTeams()){
                    System.out.println(team.toString() + Print.newline);
                }
                break;
            case 3:
                Member member = Print.readMember();
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
                Member member1 = retrieveMember(myScanner.readLine("Enter the members name: "));
                project.removeMember(member1);
                break;
            case 6:
                Print.readTeam();
                break;
            case 7:
                editTeam();
                break;
            default:
                break;
        }
    }

    private static void editMember(){
        int x = Print.printEditMemberMenu();
        while (!(x == 1 || x == 2)){
            x = Print.printEditMemberMenu();
        }
        Member member = retrieveMember(myScanner.readLine("Enter the members name: "));
        if (x == 1){

            member.setName(myScanner.readLine("Enter the members new name: "));
        }
        else {
            member.setSALARY_PER_HOUR(myScanner.readDouble("Enter the members new salary: "));
        }
    }

    private static void editTeam(){
        int x = Print.printEditSubTeamMenu();
        while (!(x == 1 || x == 2 || x == 3)){
            x = Print.printEditSubTeamMenu();
        }
        Team team = retrieveTeam(myScanner.readLine("Enter the teams name: "));
        if (x == 1){
            team.setName(myScanner.readLine("Enter the teams new name: "));
        }
        else if (x == 2) {
            Member member = retrieveMember(myScanner.readLine("Enter the members name: "));
            try {
                team.addMember(member);
            } catch (MemberIsNullException e) {
                e.printStackTrace();
            } catch (MemberAlreadyRegisteredException e) {
                e.printStackTrace();
            }
        } else {
            Member member = retrieveMember(myScanner.readLine("Enter the members name: "));
            try {
                team.removeMember(member);
            } catch (MemberIsNullException e) {
                e.printStackTrace();
            }
        }
    }

    public static Member retrieveMember(String name){
        for(Member member : project.getTeam().getMembers()){
            if(member.getName().equals(name)){
                return member;
            }
        }
        return null;
    }

    public static Team retrieveTeam(String name){
        for(Team team : project.getTeams()){
            if(team.getName().equals(name)){
                return team;
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
        return 0;
    }

    private static void projectMenu(){
        int x = Print.printProjectMenu();
        while (!(x == 1 || x == 2)){
            x = Print.printProjectMenu();
        }
        if (x == 1){
            project.toString();
        }
        else {
            editProject();
        }
    }

    public static void editProject(){
        int x = Print.printEditProjectMenu();
        while (!(x == 1 || x == 2)){
            x = Print.printEditProjectMenu();
        }
        if (x == 1){
            project.setName(Print.addName());
        }
        else {

            project.getSchedule().setEnd(Print.ender());
        }
    }



    public static void setProject(Project pro) {
        project = pro;
    }

    public static void createProject() {
        setProject(Print.createProject());
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