import java.time.DayOfWeek;

public class Activity {

    // constants
    private final static int WORKING_HOURS_PER_WEEK = 20;
    private final static int DUMMY_BILLABLE_HOURS = -1;

    // member attributes
    private String name;
    private TimePeriod timePeriod;      // can be null initially
    private Team team;                  // can be null initially
    private int billableHours;          // time this activity is supposed to take
    private int hoursCovered;           // time that has been worked effectively on this activity so far
    private double costSoFar;           // cost working on this activity has caused so far


    public Activity(String name, TimePeriod timePeriod, Team team, int billableHours) {
        if (name == null || name.trim() == "") {
            throw new IllegalArgumentException("An activity needs a name different from null or whitespace!");
        }

        this.name = name;
        this.timePeriod = timePeriod;
        setBillableHours(billableHours);
        hoursCovered = 0;
        costSoFar = 0;
        setTeam(team);
    }

    public Activity(String name, TimePeriod timePeriod, Team team) {
        this(name, timePeriod, team, TimePeriod.getDurationInWeeksStatically(timePeriod) * WORKING_HOURS_PER_WEEK);
    }

    public void work(Member member, int hoursWorked, int hoursScheduled) {
        if (!team.contains(member)) {
            throw new IllegalArgumentException("The selected Member can not work on this activity!");
        }
        if (hoursCovered >= billableHours) {
            throw new IllegalArgumentException("The activity is already completed!");
        }

        if (!(hoursCovered + hoursScheduled <= billableHours)) {
            hoursScheduled = billableHours - hoursCovered;          // limit hours to amount of work that is left
        }
        costSoFar += member.workOnActivity(this, hoursWorked, hoursScheduled);
        hoursCovered += hoursScheduled;
    }

    /**
     * Method calculating the expected cost for the team to complete the activity.
     *
     * @return average salary * expected work-duration
     */
    public double getBudgetAtCompletion() {
        return team.getAverageSalary() * ((double) getBillableHours());
    }

    /**
     * Method calculating the expected cost for the already completed part of the activity.
     *
     * @return average salary * hours of work covered so far
     */
    public double getEarnedValue() {
        if (team == null) {
            return 0;
        }
        return team.getAverageSalary() * hoursCovered;
    }

    public double getCompletion() {
        return ((double) hoursCovered) / ((double) billableHours);
    }

    public static int compareStartTime(Activity a1, Activity a2) {
        return a1.getTimePeriod().getStart().compareTo(a2.getTimePeriod().getStart());
    }

    /**
     * Method calculating the fraction of the time period between start and end
     * that has passed after day AFTER in YearWeek yearWeek.
     *
     * @param yearWeek week at which we evaluate the scheduledCost
     * @param after    weekday after which we evaluate the scheduledCost
     * @return BAC * fraction of timePeriod that has passed at the passed date
     */
    public double getScheduledCost(YearWeek yearWeek, DayOfWeek after) {
        return getBudgetAtCompletion() * timePeriod.calculatePassedFraction(yearWeek, after);
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public int getBillableHours() {
        return billableHours;
    }

    public void setBillableHours(int billableHours) {
        if (billableHours < 0) {
            throw new IllegalArgumentException("An activity can not have a negative amount of billable hours!");
        }
        this.billableHours = billableHours;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        if (team != null) {
            try {
                team.addActivity(this);
            } catch (ActivityAlreadyRegisteredException e) {
                e.printStackTrace();
            } catch (ActivityIsNullException e) {
                e.printStackTrace();
            }
        }
        this.team = team;
    }

    public double getCostSoFar() {
        return costSoFar;
    }

    public boolean setEndWeek(int week) {
        boolean success = false;
        try {
            timePeriod.setEnd(new YearWeek(timePeriod.getEnd().getYear(), week));
            success = true;
        } catch (IllegalArgumentException e) {
            Print.println("Your newly selected week is apparently not suitable with the fix start date!");
        }
        return success;
    }

    public boolean setEndYear(int year) {
        boolean success = false;
        try {
            timePeriod.setEnd(new YearWeek(year, timePeriod.getEnd().getWeek()));
            success = true;
        } catch (IllegalArgumentException e) {
            Print.println("Your newly selected year is apparently not suitable with the fix start date!");
        }
        return success;
    }

    //End of Accessor methods
}
