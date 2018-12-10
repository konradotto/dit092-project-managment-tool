import java.util.ArrayList;

public class ProjectTesting {

    public static void main(String[] args) throws RiskAlreadyRegisteredException, RiskIsNullException, RiskProbabilityNotDefinedException, RiskImpactNotDefinedException {
        ProjectSchedule schedule = new ProjectSchedule(2018, 48, 2019, 3,
                new ArrayList<Activity>());

        Project broject = new Project("", new Team(), new RiskMatrix(), schedule, new Budget());

        System.out.println(broject);

        Risk r1 = new Risk("Death", 5, 5);
        broject.getRiskMatrix().addRisk(r1);

        System.out.println(broject);

        Budget b1 = new Budget(2000, 3000, 75);
        broject.setBudget(b1);
        System.out.println(broject);
    }
}