import java.util.*;

public class Team {

    private String name;
    private ArrayList<Member> members;
    private ArrayList<Activity> activities;

    public Team(){

    }

    public Team(String name, ArrayList<Member> members, ArrayList <Activity> activities){
        this.members = members;
        this.activities = activities;
        this.name = name;
    }

    public void addMember(Member member){

    }

    public void removeMember(Member member){

    }

    public void addActivity(Activity activity){

    }

    public void removeActivity(Activity activity){

    }

    public boolean teamConstraints(UUID id){

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
