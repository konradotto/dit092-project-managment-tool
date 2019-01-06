import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Team {

    private String name;
    private ArrayList<Member> members;
    private ArrayList<Activity> activities;


    private final static int COLUMN_WIDTH = 30;
    private final static int COLUMNS = 5;

    public Team(String name){
        members = new ArrayList<>();
        activities = new ArrayList<>();
        this.name = name;
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
            throw new MemberAlreadyRegisteredException("The member is already registered!");
        } else {
            members.add(member);
        }
    }


    public void addMembers(List<Member> members)throws  MemberIsNullException, MemberAlreadyRegisteredException{
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
        } else if (false) {//activities.contains(activity)) {
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

    public boolean contains(Member member) {
        return members.contains(member);
    }

    public Member retrieveMember(String name) {
        for (Member member : members) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }


    public Member retrieveMember(int index) {
        if (index >= 0 && index < members.size()) {
            return members.get(index);
        }
        return null;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return formatTable();
    }

    private String formatTableRow(String[] columns) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < COLUMNS - 2; ++i) {
            sb.append(String.format("%1$-" + COLUMN_WIDTH + "s", columns[i]));
        }
        sb.append(String.format(columns[3] + "%n"));

        return sb.toString();
    }

    public String toNumberedString() {
        StringBuilder sb = new StringBuilder();

        if (members.isEmpty()) {
            sb.append("There are currently no members registered for this team." + System.lineSeparator());
        } else {
            int l = (int) Math.ceil(Math.log10(members.size()) + 0.005);   // number of digits needed for left aligned formatting
            for (int i = 0; i < members.size(); i++) {
                Member mem = members.get(i);
                sb.append(String.format("%1$" + String.valueOf(l) + "s %2$-20s\t%3$s%n", i + 1, mem.getName(), mem.getID()));
            }
        }

        return sb.toString();
    }

    public boolean workOnActivity(Member member, Activity activity, long timeSpent, long timeScheduled) {

        // TODO: prevent to spend more scheduled time than needed for the task


        member.spendTime(timeSpent);
        double cost = timeSpent * member.getSALARY_PER_HOUR();
        activity.spendTime(timeScheduled, timeSpent, cost);

        return true;
    }

    public void alphaSort() {
       members.sort(Comparator.comparing(Member::getName));
    }


    public String formatTable() {
            StringBuilder sb = new StringBuilder();
            String newline = System.lineSeparator();
            if (members.isEmpty()) {
                sb.append("Team: " +getName());
                sb.append("There are no members registered in this team yet." + newline);
            } else {

                sb.append("\t\t\t Team: " + getName() + newline);

                sb.append(String.join("", Collections.nCopies((COLUMNS-2) * COLUMN_WIDTH +1, "-")));
                sb.append(newline);

                // format table content
                sb.append(formatTableRow(new String[] {"| Member name:", "| Salary/h:", "| Time spent:", "|"}));

                // separator line

                sb.append(String.join("", Collections.nCopies((COLUMNS-2) * COLUMN_WIDTH + 1, "-")));
                sb.append(newline);

                alphaSort();

                for (Member member : members) {
                    sb.append(formatTableRow(new String[] {"| " + member.getName(),
                            "| " + member.getSALARY_PER_HOUR(),
                            "| " + member.getTimeSpent(), "|"}));

                    sb.append(String.join("", Collections.nCopies((COLUMNS-2) * COLUMN_WIDTH +1, "-")));
                    sb.append(newline);
                }
            }
        //test
        return sb.toString();
    }

    //GETTERS AND SETTERS
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}



    public double timeSpentPercentage(){

        double sumOfDurations = 0;
        /*for (Activity activity: activities){

            sumOfDurations += activity.getDuration();
        }*/
        double sumOfTimeSpent = 0;
        for (Member member: members){
            sumOfTimeSpent +=member.getTimeSpent();
        }

        return sumOfTimeSpent/sumOfDurations;
    }
}
