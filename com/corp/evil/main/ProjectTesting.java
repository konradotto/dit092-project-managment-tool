import java.util.ArrayList;

public class ProjectTesting {

    public static void main(String[] args) {
        ProjectSchedule schedule = new ProjectSchedule(2018, 48, 2019, 3,
                new ArrayList<Activity>());

        Project broject = new Project("Broject", new Team(), new RiskMatrix(), schedule, new Budget());

        System.out.println(broject);
    }
}
