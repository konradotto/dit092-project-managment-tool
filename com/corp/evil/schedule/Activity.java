import java.util.Calendar;

public class Activity {

    private int startWeek;
    private int endWeek;
    private String name;
    private Team team;
    private Budget budget;
    private final static int LAST_WEEK_OF_YEAR = 52;
    private final static int DAYS_IN_WORKWEEK = 5;

    private double costOfWorkScheduled;
    private double costOfWorkPerformed;
    private double percentCompleted;


    public Activity(String name, int startWeek, int endWeek, Team team) {
        this.name = name;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.team = team;
        this.costOfWorkScheduled = scheduledCost();
        this.costOfWorkPerformed = 0.0;
        this.percentCompleted = 0.0;

        try {
            team.addActivity(this);
        } catch (ActivityAlreadyRegisteredException e) {
            e.printStackTrace();
        } catch (ActivityIsNullException e) {
            e.printStackTrace();
        }
    }

    //TODO: activity constructor without team

    //Accessor methods
    public int getDuration() {
        int weeks;
        if (getStartWeek() > getEndWeek()) {
            int firstDur = LAST_WEEK_OF_YEAR - getStartWeek();
            weeks = firstDur + getEndWeek();
        } else {
            weeks = getEndWeek() - getStartWeek();
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

    public double getPercentScheduled() {
        Calendar cal = Calendar.getInstance();
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        return getPercentSchedule(week, day);
    }

    public double getPercentSchedule(int week, int day) {
        int totalWeeks = (endWeek >= startWeek) ? (endWeek - startWeek + 1) :
                (endWeek + LAST_WEEK_OF_YEAR) - startWeek + 1;
        int weeksPassed = (week >= startWeek) ? week - startWeek : (week + LAST_WEEK_OF_YEAR) - startWeek;

        int daysPassed = (day < Calendar.SATURDAY) ? (day - Calendar.SUNDAY) : DAYS_IN_WORKWEEK;

        return (weeksPassed + (daysPassed / DAYS_IN_WORKWEEK)) / totalWeeks;
    }

    public double scheduledCost() {
        double averageSalary = 0.0;
        int teamSize = team.getMembers().size();
        for (Member member : team.getMembers()) {
            averageSalary += member.getSALARY_PER_HOUR();
        }
        return (averageSalary /= teamSize) * getDuration();
    }

    public void spendTime(long timeScheduled, double cost) {
        costOfWorkPerformed += cost;
        percentCompleted += ((double) timeScheduled) / ((double) getDuration());
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
