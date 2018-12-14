import java.util.Collections;


public class ConsoleProgram {

    public static void main(String[] args) {

        choice = printStartMenu();

        switch (choice) {

            case 1:
                // load existing project
                //toString oject print
                break;

            case 2:
                // load default project
                //toString oject print
                break;
        }

        do {

            choice = printEditingMenu();

            switch (choice) {

                case 1:
                    String name = printEditingName();
                    setProjectName(name);
                    break;

                case 2:

                    // EDITING TEAM
                    do {
                        // print team Team.formattable();
                        choice = printEditingTeam();

                        switch (choice) {

                            case 1:
                                addMember();
                            case 2:
                                removeMember();
                                break;
                            case 3:
                                addActivity();
                                break;
                            case 4:
                                removeActivity();
                                break;
                            case 5:
                                workOnActivity();
                                break;

                        }
                        while (choice != 6)

                            // EDITING RISKMATRIX
                            case 3:
                        //print riskmatrix

                        choice = printEditingRiskMatrix();

                        switch (choice) {

                            case 1:
                                //add risk - print how
                                addRisk();
                                break;
                            case 2:
                                //remove risk - print how
                                removeRisk();
                                break;

                            break;

                            // EDITING SCHEDULE
                            case 4:

                                choice = printEditingSchedule();
                                switch(choice)
                                case 1:
                                    //print how to add
                                    addActivity();
                                break;
                                case 2:
                                    //print how to remove
                                    removeActivity();
                                    break;


                                break;
                        }
                        while (choice != 6)

                    }
            }
        }







