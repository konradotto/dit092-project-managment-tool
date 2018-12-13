import java.util.ArrayList;

public class ProjectTesting {

    public static void main(String[] args) throws RiskAlreadyRegisteredException, RiskIsNullException, RiskProbabilityNotDefinedException, RiskImpactNotDefinedException, NameIsEmptyException, MemberAlreadyRegisteredException, MemberIsNullException, ActivityAlreadyRegisteredException, ActivityIsNullException {
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
}