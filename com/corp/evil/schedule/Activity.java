import java.util.Map;
import java.util.TreeMap;

public class Activity {

    private int startWeek;
    private int endWeek;
    private int endYear;
    private int startYear;
    private String name;
    private Team team;
    private Budget budget;
    private final static int LAST_WEEK_OF_YEAR = 52;
    private final static int DAYS_IN_WORKWEEK = 5;

    private double costOfWorkScheduled;
    private double costOfWorkPerformed;
    private double percentCompleted;
    private Map<Member, Long> timeSpent;


    public Activity(String name, int startWeek, int startYear, int endWeek, int endYear) {
        this(name, startWeek, startYear, endWeek, endYear, null);
    }

    public Activity(String name, int startWeek, int startYear, int endWeek, int endYear, Team team) {
        this.name = name;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.team = team;
        this.costOfWorkScheduled = scheduledCost();
        this.costOfWorkPerformed = 0.0;
        this.percentCompleted = 0.0;
        this.startYear = startYear;
        this.endYear = endYear;
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
    public int getDuration() {
        int weeks;
        if (getStartWeek() > getEndWeek()) {
            int firstDur = LAST_WEEK_OF_YEAR - getStartWeek() + 1;
            weeks = firstDur + getEndWeek();
        } else {
            weeks = getEndWeek() - getStartWeek() + 1;
        }
        int hours = weeks * 20;
        return hours;
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
        System.err.println("Duration: " + getDuration());

        return (averageSalary) * getDuration();
    }

    public void spendTime(long timeScheduled, long timeSpent, double cost) {
        costOfWorkPerformed += cost;
        costOfWorkScheduled += timeSpent;
        percentCompleted += ((double) timeScheduled) / ((double) getDuration()) * 100.0;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }


    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
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
