import java.util.ArrayList;

public class ProjectTesting {

    public static void main(String[] args) throws RiskAlreadyRegisteredException, RiskIsNullException, RiskProbabilityNotDefinedException, RiskImpactNotDefinedException, NameIsEmptyException, MemberAlreadyRegisteredException, MemberIsNullException {
        ProjectSchedule schedule = new ProjectSchedule(2018, 48, 2019, 3,
                new ArrayList<Activity>());

        Project broject = new Project("", new Team(), new RiskMatrix(), schedule);

        System.out.println(broject);
        broject.getTeam().addMember(new Member("Dude", 5000));

        Risk r1 = new Risk("Death", 5, 5);
        broject.getRiskMatrix().addRisk(r1);

        System.out.println(broject);

        Budget b1 = new Budget(2000, 3000, 75);
        System.out.println(broject);
    }
}