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

    public void addMember(Member member){
        members.add(member);
    }

    public void removeMember(Member member){
        members.remove(member);
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void removeActivity(Activity activity){
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
