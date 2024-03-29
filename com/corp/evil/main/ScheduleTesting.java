import java.util.ArrayList;

public class ScheduleTesting {

    public static void main(String[] args) throws NameIsEmptyException, MemberAlreadyRegisteredException, MemberIsNullException, ActivityAlreadyRegisteredException, ActivityIsNullException {

        ProjectSchedule schedule = new ProjectSchedule(new TimePeriod(2018, 49, 2018, 49), new ArrayList<>());
        System.out.println(schedule);

        Member m1 = new Member("lil pump", 100.0);
        Team t1 = new Team("Team 1");
        t1.addMember(m1);


        Member m2 = new Member("lil wayne", 100.0);
        Team t2 = new Team("t2");

        Activity a1 = new Activity("planning", new TimePeriod(35, 2017, 20, 2018), t1);
        Activity a2 = new Activity("testing", new TimePeriod(21, 2019, 30, 2018), t2);
        schedule.addActivity(a1);
        schedule.addActivity(a2);

        System.out.println(schedule.getStartWeek());
        System.out.println(schedule.getEndWeek());



        System.out.println(schedule.toString());
    }
}
