import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectSchedule {

    // constants for table formatting
    private final static int COLUMN_WIDTH = 25;
    private final static int COLUMNS = 5;
    private final static String LS = Print.LS;
    private final static String SEPARATOR = String.join("", Collections.nCopies((COLUMNS) * COLUMN_WIDTH + 1, "-")) + LS;
    private final static String HEAD;

    // initialising the static header part of every ProjectSchedule
    static {
        StringBuilder sbTemp = new StringBuilder();
        sbTemp.append("\t\t\t TASKS " + LS);
        sbTemp.append(SEPARATOR);
        sbTemp.append(formatTableRow(new String[]{"| Task name:", "| Start Week, Year:", "| End Week, Year:", "| Percent Completed:", "| Teams: ", "|"}));
        sbTemp.append(SEPARATOR);

        HEAD = sbTemp.toString();
    }

    // member variables
    private ArrayList<Activity> activities;
    private TimePeriod timePeriod;

    public ProjectSchedule(TimePeriod timePeriod, ArrayList<Activity> activities) {
        this.timePeriod = timePeriod;
        this.activities = activities;
    }

    public ProjectSchedule(TimePeriod timePeriod) {
        this(timePeriod, new ArrayList<>());
    }


    /**
     * Reference the passed project member instead of a separate object for all activity team members
     *
     * @param team Team to be looked for.
     */
    public void solveCopies(Team team) {
        for (Member member : team.getMembers()) {
            for (Activity activity : activities) {
                if (activity.hasTeam()) {
                    activity.getTeam().solveCopies(member);
                }
            }
        }
    }

    public void sort() {
        activities.sort(Activity::compareStartTime);
    }

    /**
     * The earned value is defined as the
     *
     * @return
     */
    public double getEarnedValue() {
        double earnedValue = 0.0;

        for (Activity act : activities) {
            earnedValue += act.getEarnedValue();                // sum up completed costs
        }
        return earnedValue;
    }

    /**
     * The cost variance is defined as the difference between the earned value and the actual (personnel-)cost
     * of the work conducted at any point of the project.
     *
     * @return earnedValue - actualCost
     */
    public double getCostVariance() {
        double actualCost = 0.0;

        for (Activity act : activities) {
            actualCost += act.getCostSoFar();
        }
        return getEarnedValue() - actualCost;
    }

    /**
     * @return
     */
    public double getScheduledCost(YearWeek yearWeek, DayOfWeek day) {
        double scheduledCost = 0.0;

        for (Activity act : activities) {
            scheduledCost += act.getScheduledCost(yearWeek, day);
        }
        return scheduledCost;
    }

    /**
     * The schedule variance is defined as the difference between the earned value
     * and the cost of the work that should be done by this time of the project.
     *
     * @return earned value - scheduled cost
     */
    public double getScheduleVariance(YearWeek yearWeek, DayOfWeek after) {
        return getEarnedValue() - getScheduledCost(yearWeek, after);
    }

    /**
     * Add an activity to a schedule.
     *
     * @param activity the activity that is supposed to be added to the schedule
     * @throws ActivityAlreadyRegisteredException
     * @throws ActivityIsNullException
     */
    public void addActivity(Activity activity) throws ActivityAlreadyRegisteredException, ActivityIsNullException {
        if (activity == null) {
            throw new ActivityIsNullException("Activity is NULL and cannot be added to the list of activities!");
        }

        if (activities.contains(activity)) {
            throw new ActivityAlreadyRegisteredException("This activity is already registered!");
        }
        if (!activity.getTimePeriod().isWithin(timePeriod)) {
            throw new IllegalArgumentException("The activity's period is not within the time frame of the schedule!");
        }

        activities.add(activity);
    }

    public void removeActivity(Activity activity) throws ActivityIsNullException {
        if (activity == null) {
            throw new ActivityIsNullException("Project schedule can not remove NULL value from schedule");
        } else {
            activities.remove(activity);
        }
    }

    @Override
    public String toString() {
        return formatTable();
    }

    private static String formatTableRow(String[] columns) {
        String result = "";
        for (int i = 0; i < COLUMNS; ++i) {
            result += String.format("%1$-" + COLUMN_WIDTH + "s", columns[i]);
        }
        result += String.format(columns[columns.length - 1] + "%n");

        return result;
    }

    public String formatTable() {
        StringBuilder sb = new StringBuilder();

        if (activities.isEmpty()) {
            sb.append("There are no activities registered to this project schedule yet." + LS);
        } else {
            sb.append(HEAD);

            this.sort();        // sort the activities by their start date

            for (Activity activity : activities) {
                if (activity.getTeam() != null) {

                    sb.append(formatTableRow(new String[]{"| " + activity.getName(),
                            "| " + activity.getTimePeriod().getStart().toString(),
                            "| " + activity.getTimePeriod().getEnd().toString(),
                            "| " + String.format("%.2f", activity.getCompletion() * 100.0),
                            "| " + activity.getTeam().getName(),
                            "|"
                    }));
                } else {
                    sb.append(formatTableRow(new String[]{"| " + activity.getName(),
                            "| " + activity.getTimePeriod().getStart().toString(),
                            "| " + activity.getTimePeriod().getEnd().toString(),
                            "| " + String.format("%.2f", activity.getCompletion() * 100.0),
                            "| " + "No team assigned",
                            "|"
                    }));
                }
                sb.append(SEPARATOR);
            }
        }
        return sb.toString();
    }


    public List<Activity> getParticipation(Member member) {
        List<Activity> result = new ArrayList<>();
        for (Activity act : this.activities) {
            if (act.getTeam().contains(member)) {
                result.add(act);
            }
        }
        return result;
    }

    public int getStartWeek() {
        return timePeriod.getStart().getWeek();
    }

    public int getEndWeek() {
        return timePeriod.getEnd().getWeek();
    }

    public void setEndWeek() {

    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        for (Activity activity : activities) {
            if (!activity.getTimePeriod().isWithin(timePeriod)) {
                throw new IllegalArgumentException("The new TimePeriod conflicts with the existing activities! Update aborted.");
            }
        }
        this.timePeriod = timePeriod;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }
}
