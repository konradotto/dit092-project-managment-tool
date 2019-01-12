import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ProjectSchedule {

    private final static int FIRST_WORKDAY = Calendar.MONDAY;
    public final static int LAST_WORKDAY = Calendar.FRIDAY;
    private final static int DAY_START_HOUR = 8;
    public final static int DAY_END_HOUR = 17;
    private final int LAST_WEEK_OF_YEAR = 52;

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
        sbTemp.append(formatTableRow(new String[]{"| Task name:", "| Start YearWeek:", "| End YearWeek:", "| Percent Completed:", "| Teams: ", "|"}));
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
            throw new IllegalArgumentException("The activities period is not within the time frame of the schedule!");
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
                            "| " + activity.getTimePeriod().getStart().getWeek(),
                            "| " + activity.getTimePeriod().getEnd().getWeek(),
                            "| " + String.format("%.2f", activity.getCompletion() * 100.0),
                            "| " + activity.getTeam().getName(),
                            "|"
                    }));
                } else {
                    sb.append(formatTableRow(new String[]{"| " + activity.getName(),
                            "| " + activity.getTimePeriod().getStart().getWeek(),
                            "| " + activity.getTimePeriod().getEnd().getWeek(),
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
        this.timePeriod = timePeriod;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }
}
