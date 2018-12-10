import java.util.ArrayList;

public class TeamTesting{

    public static void main (String[] args) throws ActivityAlreadyRegisteredException, ActivityIsNullException, MemberAlreadyRegisteredException, MemberIsNullException, NameIsEmptyException{


    ArrayList<Activity> activities = new ArrayList<>();
    ArrayList<Member> members = new ArrayList<>();
    Team t1 = new Team("lil pump", members, activities);

    Member m1 = new Member("lil pump", 100.0);
    Activity a1 = new Activity("A lil pump activity", 13, 15, t1, null);

    System.out.print(a1.toString());

    t1.addMember(m1);

    System.out.println(t1.toString());

    }
}
