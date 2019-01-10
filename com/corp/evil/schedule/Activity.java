import java.util.Map;
import java.util.TreeMap;

public class Activity {

    private String name;
    private TimePeriod timePeriod;
    private Team team;
    private Budget budget;
    private final static int WORKING_HOURS_PER_WEEK = 20;

    private double costOfWorkScheduled;
    private double costOfWorkPerformed;
    private double percentCompleted;
    private Map<Member, Long> timeSpent;


    public Activity(String name, TimePeriod timePeriod) {
        this(name, timePeriod, null);
    }

    public Activity(String name, TimePeriod timePeriod, Team team) {
        this.name = name;
        this.timePeriod = timePeriod;
        this.team = team;
        this.costOfWorkScheduled = scheduledCost();
        this.costOfWorkPerformed = 0.0;
        this.percentCompleted = 0.0;
        this.timeSpent = new TreeMap<Member, Long>();
        if (team!=null){
            try {
                team.addActivity(this);
            } catch (ActivityAlreadyRegisteredException e) {
                e.printStackTrace();
            } catch (ActivityIsNullException e) {
                e.printStackTrace();
            }
        }
    }
//TODO: activity constructor without team

    //Accessor methods
    public int getDurationInHours() {
        return timePeriod.getDurationInWeeks() * WORKING_HOURS_PER_WEEK;
    }

    public double getPercentCompleted() {
        return percentCompleted;
    }

    public double getCostOfWorkScheduled() {
        return costOfWorkScheduled;
    }

    public double getCostOfWorkPerformed() {
        return costOfWorkPerformed;
    }

    public void setCostOfWorkScheduled(double costOfWorkScheduled) {
        this.costOfWorkScheduled = costOfWorkScheduled;
    }

    public double getPercentScheduled() {

        //TODO
        return 0;
    }

    public double scheduledCost() {
        double averageSalary = 0.0;
        if ((team == null) || (team.getMembers().isEmpty())){
            return 0.0;
        }
        int teamSize = team.getMembers().size();
        for (Member member : team.getMembers()) {
            averageSalary += member.getSALARY_PER_HOUR();
        }
        averageSalary /= teamSize; //why are we dividing here!?
        System.err.println("Schedule Cost()");
        System.err.println(team.getName());
        System.err.println("Average Salary: " + averageSalary);
        System.err.println("Team Size: " + teamSize);
        System.err.println("Duration: " + getDurationInHours());

        return (averageSalary) * getDurationInHours();
    }

    public void spendTime(long timeScheduled, long timeSpent, double cost) {
        costOfWorkPerformed += cost;
        costOfWorkScheduled += timeSpent;
        percentCompleted += ((double) timeScheduled) / ((double) getDurationInHours()) * 100.0;
    }

    public int getStartWeek() {
        return timePeriod.getStartWeek();
    }

    public int getEndWeek() {
        return timePeriod.getEndWeek();
    }

    public int getEndYear() {
        return timePeriod.getEndYear();
    }

    public int getStartYear() {
        return timePeriod.getStartYear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
    //End of Accessor methods
}
