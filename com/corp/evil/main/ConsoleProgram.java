/**
 * Class responsible to keep track of the current state (finite state machine of menus),
 * input-output and hosting the project.
 */
public class ConsoleProgram {

    // status constants
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
                    primaryMenu();

                    break;
                default:
                    break;
            }
        } while (proceed);

        return SUCCESS;     // return success after finishing the program
    }

    /**
     * @return
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
            default:
                Print.println("Choose a valid option!" + Print.newline);
                next = PROJECT;
                break;
        }

        return next;
    }


    public static void primaryMenu() {
        do {
            switch (Print.printPrimaryMeny()) {
                case PRIMARY_PROJECT:
                    projectMenu();
                    proceed = false;
                    break;
                case PRIMARY_TEAM:
                    teamMenu();
                    proceed = false;
                    break;
                case PRIMARY_TASK:
                    taskManager();
                    proceed = false;
                    break;
                case PRIMARY_RISK:
                    riskManager();
                    proceed = false;
                    break;
                case PRIMARY_BUDGET:
                    if (project.getBudget()!=null){
                        System.out.println(project.getBudget());
                    }
                    else {
                        System.out.println("No budget data yet!");
                    }
                    proceed = false;
                    break;
                case PRIMARY_SAVE_EXIT:
                    // TODO save and exit method
                    proceed = true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        }while (!proceed);
    }



    public static void taskManager() {
      do {
          switch (Print.printTasksMenu()) {
              case 1:
                  if (project.getSchedule().getActivities()!=null) {
                          System.out.println(project.getSchedule());
                  }else {
                      System.out.println("No registered tasks");
                  }
                  proceed = false;
                  break;
              case 2:
                  try {
                      project.getSchedule().addActivity(Print.createActivity());
                      proceed = false;
                  } catch (ActivityAlreadyRegisteredException e) {
                      e.getMessage();
                      proceed = false;
                  } catch (ActivityIsNullException e) {
                      e.getCause();
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
      }while (!proceed);
    }

    public static void taskTimeSetter() {


        // TODO logic for workOnActivty method
    }

    public static void taskAssigner()  {
        Activity task = null;
        try {
            task = Print.readActivity();
        } catch (ActivityIsNullException e) {
            System.out.println(e + Print.newline);
        }
        Team team = null;
        try {
            team = Print.readTeam();
        } catch (TeamIsNullException e) {
            System.out.println(e + Print.newline);
        }
        try {
                team.addActivity(task);
                task.setTeam(team); //TODO: not sure about this line. Do we still need a team for an activity(since teams are saved separately in their list)?
            } catch (ActivityAlreadyRegisteredException e) {
                e.printStackTrace();
            } catch (ActivityIsNullException e) {
                e.printStackTrace();
            }
    }

    public static void taskRemover() {
        Activity task = null;
        try {
            task = Print.readActivity();
        } catch (ActivityIsNullException e) {
            System.out.println(e + Print.newline);
        }
        try {
                project.getSchedule().removeActivity(task);
            } catch (ActivityIsNullException e) {
                System.out.println(e + Print.newline);
            }

    }

    public static void editTask(){
        Activity activity = null;
        try {
            activity = Print.readActivity();
        } catch (ActivityIsNullException e) {
            System.out.println(e + Print.newline);
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
    }



    private static void riskManager() {
        do {
            switch (Print.printRiskMenu()){

                case 1:
                    if (project.getRiskMatrix()!=null) {
                       System.out.println(project.getRiskMatrix().toStringText());
                    }else {
                        System.out.println("No registered risks!");
                    }
                    proceed=false;
                    break;
                case 2:
                    try {
                        project.getRiskMatrix().addRisk(Print.createRisk());
                        proceed=false;
                    } catch (RiskIsNullException e) {
                        e.toString();
                        proceed=false;
                    } catch (RiskAlreadyRegisteredException e) {
                        e.printStackTrace();
                        proceed=false;
                    }
                    break;
                case 3:
                    try {
                        project.getRiskMatrix().removeRisk(Print.readRisk());
                        proceed=false;
                    } catch (RiskIsNullException e) {
                        System.out.println(e + Print.newline);
                        proceed=false;
                    }
                    break;
                case 4: //Back to previous menu
                    proceed=true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        }while (!proceed);
    }



    private static void teamMenu() {
        do {
            switch (Print.printTeamMenu()) {
                case 1:
                    if (project.getTeam() != null){
                        System.out.println(project.getTeam().toString() + Print.newline);

                    }else {
                        System.out.println("No registered members!" + Print.newline);
                    }
                    proceed = false;
                    break;
                case 2:
                    if (project.getTeams() != null){
                        for (Team team : project.getTeams()) {
                            System.out.println(team + Print.newline);
                        }

                    }else{
                        System.out.println("No registered teams!" + Print.newline);
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
        }while (!proceed);
    }

    private static void editMember() {
        Member member = null;
        try {
            member = Print.readMember();
        } catch (MemberIsNullException e) {
            System.out.println(e + Print.newline);
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

    }

    private static void editTeam() {
        Team team = null;
        try {
            team = Print.readTeam();
        } catch (TeamIsNullException e) {
            System.out.println(e + Print.newline);
        }
        do {
            switch (Print.printEditSubTeamMenu()){

                case 1:
                    team.setName(myScanner.readLine("Enter the teams new name: "));
                    proceed=false;
                    break;
                case 2:
                    try {
                        team.addMember(Print.readMember());
                        proceed=false;
                    } catch (MemberIsNullException e) {
                        e.printStackTrace();
                        proceed=false;
                    } catch (MemberAlreadyRegisteredException e) {
                        e.printStackTrace();
                        proceed=false;
                    }
                    break;
                case 3:
                    try {
                        team.removeMember(Print.readMember());
                        proceed=false;
                    } catch (MemberIsNullException e) {
                        e.printStackTrace();
                        proceed=false;
                    }
                    break;
                case 4:
                    proceed=true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        }while (!proceed);
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
            switch (Print.printProjectMenu()){
                case 1:
                    Print.printProject(project);
                    proceed=false;
                    break;
                case 2:
                    editProject();
                    proceed=false;
                    break;
                case 3:
                    proceed=true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        }while (!proceed);
    }

    public static void editProject() {

        do {
            switch (Print.printEditProjectMenu()){

                case 1:
                    project.setName(Print.enterName());
                    proceed=false;
                    break;
                case 2:
                    project.getSchedule().setEnd(Print.ender());
                    proceed=false;
                    break;
                case 3:
                    proceed=true;
                    break;
                default:
                    System.out.println("Choose a valid option!\n");
                    proceed = false;
                    break;
            }
        }while (!proceed);
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
