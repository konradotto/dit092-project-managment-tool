import java.util.ArrayList;

public class ScheduleTesting {

    public static void main(String[] args) throws NameIsEmptyException, MemberAlreadyRegisteredException, MemberIsNullException, ActivityAlreadyRegisteredException, ActivityIsNullException {

        ProjectSchedule schedule = new ProjectSchedule(2018, 49, 2018, 49,  new ArrayList<>());
        System.out.println(schedule);
        Member m1 = new Member("lil pump", 100.0);
        Team t1 = new Team();
        t1.setName("name");
        t1.addMember(m1);

        Activity a1 = new Activity("planning", 12, 20, t1);
        Activity a2 = new Activity("testing", 21, 30, t1);
        schedule.addActivity(a1);
        schedule.addActivity(a2);

        System.out.println(schedule.getStartWeek());
        System.out.println(schedule.getEndWeek());


        System.out.println(schedule.toString());
    }
}
