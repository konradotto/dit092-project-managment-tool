import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectTesting {

    public static void main(String[] args) throws NameIsEmptyException, MemberAlreadyRegisteredException, MemberIsNullException, ActivityAlreadyRegisteredException, ActivityIsNullException {


        createTestProject();


    }

    private static void testSomething() throws ActivityAlreadyRegisteredException, ActivityIsNullException, RiskAlreadyRegisteredException, RiskIsNullException, NameIsEmptyException, RiskProbabilityNotDefinedException, RiskImpactNotDefinedException, MemberIsNullException, MemberAlreadyRegisteredException {
        ProjectSchedule schedule = new ProjectSchedule(2018, 48, 2019, 3,
                new ArrayList<Activity>());

        Project broject = new Project("", new Team(), new RiskMatrix(), schedule);

        System.out.println(broject);
        Member dude = new Member("Dude", 5000);
        broject.addMember(dude);

        Risk r1 = new Risk("Death", 5, 5);
        broject.getRiskMatrix().addRisk(r1);

        System.out.println(broject);

        Budget b1 = new Budget(2000, 3000, 75);
        System.out.println(broject);

        Member theDude = new Member("The dude", 500);
        broject.addMember(theDude);
        Team team1 = new Team();
        team1.addMember(dude);
        Activity act1 = new Activity("Test Activity", 12, 18, team1);
        broject.getSchedule().addActivity(act1);
        System.out.println(broject);
        System.out.println(broject.getSchedule().getEarnedValue());
        System.out.println(broject.getSchedule().getCostVariance());
        System.out.println(broject.getSchedule().getScheduleVariance());

        team1.workOnActivity(theDude, act1, 5, 12);

        System.out.println(broject);
        System.out.println(broject.getSchedule().getEarnedValue());
        System.out.println(broject.getSchedule().getCostVariance());
        System.out.println(broject.getSchedule().getScheduleVariance());
    }

    private static void loadProject() throws IOException {

        Project testProject = JsonReaderWriter.fromJsonFile(new File("testProject.json"), Project.class, JsonReaderWriter.STANDARD_ENCODING);

        System.out.println(testProject);
    }

    public static void createTestProject() throws NameIsEmptyException, MemberIsNullException, MemberAlreadyRegisteredException, ActivityAlreadyRegisteredException, ActivityIsNullException {

        Project testProject = new Project("Test Project", new Team(), new RiskMatrix(), new ProjectSchedule());
        Member bjorn = new Member("Björn Borg", 10000);
        testProject.addMember(bjorn);
        Member zlatan = new Member("Zlatan Ibrahimovic", 70000);
        testProject.addMember(zlatan);
        Member ingrid = new Member("Ingrid Bergmann", 790);
        testProject.addMember(ingrid);
        Member greta = new Member("Greta Garbo", 790);
        testProject.addMember(greta);
        Member alfred = new Member("Alfred Nobel", 40);
        testProject.addMember(alfred);

        Team swedishNationalTeam = new Team();
        swedishNationalTeam.addMember(bjorn);
        swedishNationalTeam.setName("Team Zlatan");
        swedishNationalTeam.addMember(zlatan);

        Activity act2 = new Activity("Invent Dynamite", 49, 49, swedishNationalTeam);
        testProject.getSchedule().addActivity(act2);

        Team anotherTeam = new Team();
        anotherTeam.addMember(greta);
        anotherTeam.addMember(bjorn);
        anotherTeam.addMember(ingrid);
        anotherTeam.setName("Cheap Workforce");


        //testProject.addActivity("tHis iS an aCtiVitY", 30, 52, anotherTeam);

        Activity act = new Activity("Being lame", 44, 5, anotherTeam);
        //testProject.getSchedule().addActivity(act);

        //anotherTeam.workOnActivity(zlatan, act, 12, 5);
        //anotherTeam.workOnActivity(greta, act, 20, 20);

        swedishNationalTeam.workOnActivity(zlatan, act2, 15, 19);

        System.out.println(testProject);
        System.out.println(String.format("%.2f", testProject.getCostVariance()));
        System.out.println(String.format("%.2f", testProject.getScheduleVariance()));

        //JsonReaderWriter.setFile(new File("testProject.json"));
        //JsonReaderWriter.write(JsonReaderWriter.toJson(testProject));
    }
}