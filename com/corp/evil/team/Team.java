import java.util.*;

public class Team {

    private String name;
    private ArrayList<Member> members;
    private ArrayList<Activity> activities;

    public Team(){

    }

    public Team(String name, ArrayList<Member> members, ArrayList <Activity> activities)throws NameIsEmptyException {
        if (name.isEmpty()){ throw new NameIsEmptyException("The field name cannot be empty!"); }
        this.name = name;
        this.members = members;
        this.activities = activities;

    }


    public void addMember(Member member) throws MemberIsNullException, MemberAlreadyRegisteredException {
        if (member == null) { throw new MemberIsNullException("This member does not exist!");
        } else if (members.contains(member)) {
            throw new MemberAlreadyRegisteredException("One member cannot be added to the same group twice!");
        } else {
            this.members.add(member);
        }
    }


    public void addMember(List<Member> members)throws MemberIsNullException, MemberAlreadyRegisteredException{
        for (Member member :members){
            addMember(member);
        }

    }

    public void removeMember(Member member)throws MemberIsNullException {
        if (member == null) { throw new MemberIsNullException("This member does not exist!"); }
        members.remove(member);
    }

    public void addActivity(Activity activity) throws ActivityAlreadyRegisteredException, ActivityIsNullException{
        if (activity == null) { throw new ActivityIsNullException("This activity does not exist!");
        } else if (activities.contains(activity)) {
            throw new ActivityAlreadyRegisteredException("An activity with same name exists already!");
        } else {
            activities.add(activity);
        }
    }



    public void addActivity(List<Activity> activities)throws ActivityAlreadyRegisteredException, ActivityIsNullException {
        for (Activity activity : activities) {
            addActivity(activity);
        }

    }



    public void removeActivity(Activity activity) throws ActivityIsNullException {
        if (activity == null){ throw new ActivityIsNullException("This activity does not exist!");}
        activities.remove(activity);
    }

    public boolean teamContains(String id){

        for (Member member : this.members) {
            if(member != null && member.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", members=" + members +
                ", activitis=" + activities +
                '}';
    }

    //GETTERS AND SETTERS
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
