import java.util.ArrayList;

public class TeamTesting{

    public static void main(String[] args) throws MemberAlreadyRegisteredException, MemberIsNullException, NameIsEmptyException {



    ArrayList<Activity> activities = new ArrayList<>();
    ArrayList<Member> members = new ArrayList<>();
    Team t1 = new Team("lil pump", members, activities);

    Member m1 = new Member(" ", 100.0);
    Activity a1 = new Activity("A lil pump activity", 12, 2017 ,  20, 2018, t1);


        //System.out.println(a1.getDuration());

    t1.addMember(m1);
    System.out.println(t1.toString());



    }
}
