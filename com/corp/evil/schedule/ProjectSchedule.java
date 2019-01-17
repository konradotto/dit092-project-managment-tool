import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectSchedule {

    // constants for table formatting
    private final static int COLUMN_WIDTH = 25;
    private final static int COLUMNS = 5;
    private final static String LS = Print.LS;
    private final static String SEPARATOR = String.join("", Collections.nCopies((COLUMNS) * COLUMN_WIDTH + 1, "-")) + LS;
    private final static String HEAD;
    private final static int MIN_SPACE_PER_WEEK = 3;
    private final static int SPACES = 2;

    // initialising the static header part of every ProjectSchedule
    static {
        StringBuilder sbTemp = new StringBuilder();
        sbTemp.append("\t\t\t TASKS " + LS);
        sbTemp.append(SEPARATOR);
        sbTemp.append(formatTableRow(new String[]{"| Task name:", "| Start: Week, Year:", "| End: Week, Year:", "| Percent Completed:", "| Teams: ", "|"}));
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
        activities.sort(Activity::order);
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
        this.sort();
        return toAsciiString();
        //return formatTable();
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

    public String toAsciiString() {
        StringBuilder sb = new StringBuilder();
        String headline = "Project Schedule for " + ConsoleProgram.getProject().getName() + ":" + Print.LS;
        sb.append(headline);

        int numberOfWeeks = timePeriod.getDurationInWeeks();

        // define the length of all week columns
        int spacePerWeek = (int) Math.log10(numberOfWeeks + 1) + 2;
        if (spacePerWeek < MIN_SPACE_PER_WEEK) {
            spacePerWeek = MIN_SPACE_PER_WEEK;
        }

        String columnHeader = "Task Name";
        int longestActivityName = columnHeader.length();

        // define the length of the first column
        for (Activity activity : activities) {
            if (activity.getName().length() > longestActivityName) {
                longestActivityName = activity.getName().length();
            }
        }

        sb.append(String.join("", Collections.nCopies((longestActivityName + SPACES) +
                (numberOfWeeks * spacePerWeek) + 2 * Project.MARGIN, "-")) + LS);


        // append row of weeks
        sb.append(Print.stretchString("", longestActivityName + SPACES, ' '));
        for (int i = 0; i < numberOfWeeks; i++) {
            String s = "w" + (i + 1);
            sb.append(Print.stretchString(s, spacePerWeek, ' '));
        }
        sb.append(Print.LS);

        // append row for first column's header
        sb.append(columnHeader);
        sb.append(Print.LS);

        String emptyWeekCell = Print.stretchString("", spacePerWeek, ' ');
        String firstWeek = Print.stretchString(" ", spacePerWeek, '*');
        String week = Print.stretchString("", spacePerWeek, '*');

        // append one row per activity
        for (Activity act : activities) {
            sb.append(Print.stretchString(act.getName(), longestActivityName + SPACES, ' '));

            // calculate the amount of empty weeks
            int emptyBefore = new TimePeriod(timePeriod.getStart(), act.getTimePeriod().getStart()).getDurationInWeeks() - 1;
            for (int i = 0; i < emptyBefore; i++) {
                sb.append(emptyWeekCell);
            }

            // one starting cell with a star less in the beginning
            sb.append(firstWeek);

            // append rest of the weeks for that activity
            for (int i = 0; i < act.getTimePeriod().getDurationInWeeks() - 1; i++) {
                sb.append(week);
            }

            // make last week one star shorter than normal weeks:
            sb.setCharAt(sb.length() - 1, ' ');

            // end line
            sb.append(Print.LS);
        }

        return sb.toString();
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
