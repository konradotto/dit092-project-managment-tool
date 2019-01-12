import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;

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
        //activities.sort(Comparator.comparingInt(Activity::getStartWeek));
        //activities.sort(Comparator.comparingInt(Activity::getStartYear));
    }

    public double getEarnedValue() {
        double completion = 0.0;
        double budgetAtCompletion = 0.0;
        long totalDuration = 0;


        for (Activity act : activities) {
            long duration = (long) act.getDurationInHours();
            totalDuration += duration;
            completion += act.getPercentCompleted() * duration;         // weight completion of activities with their expected duration
            budgetAtCompletion += act.getCostOfWorkScheduled();         // sum up the expected costs
        }

        completion /= (double) totalDuration;

        System.err.println("Total Duration: " + totalDuration);
        System.err.println("Completion: " + completion);
        System.err.println("Budget at Completion: " + budgetAtCompletion);
        // normalize after completions are weighted

        return budgetAtCompletion * (completion / 100.0);
    }

    public double getCostVariance() {

        double budgetedCost = 0;
        double actualCost = 0;

        for (Activity act : activities) {
            budgetedCost += act.getCostOfWorkScheduled() * act.getPercentCompleted() / 100.0;
            actualCost += act.getCostOfWorkPerformed();
        }

        return budgetedCost - actualCost;
    }

    public double getScheduledCost() {
        double scheduledCost = 0;

        for (Activity act : activities) {
            System.err.println(act.getName());
            System.err.println("Percent: " + act.getPercentCompleted());
            System.err.println("Cost scheduled total: " + act.getCostOfWorkScheduled());
            System.err.println("Cost scheduled by now: " + act.getPercentScheduled() * act.getCostOfWorkScheduled());
            scheduledCost += act.getPercentScheduled() * act.getCostOfWorkScheduled();
        }

        return scheduledCost;
    }

    public double getScheduleVariance() {

        return getEarnedValue() - getScheduledCost();
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
                            "| " + String.format("%.2f", activity.getPercentCompleted()),
                            "| " + activity.getTeam().getName(),
                            "|"
                    }));
                } else {
                    sb.append(formatTableRow(new String[]{"| " + activity.getName(),
                            "| " + activity.getTimePeriod().getStart().getWeek(),
                            "| " + activity.getTimePeriod().getEnd().getWeek(),
                            "| " + String.format("%.2f", activity.getPercentCompleted()),
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

    public static LocalDateTime getLocalDateTime(int year, int week, int day, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, day);

        // local date time requires a TimeZone which instant does not provide
        TimeZone tz = cal.getTimeZone();
        ZoneId zid = (tz == null) ? ZoneId.systemDefault() : tz.toZoneId();
        LocalDateTime dt = LocalDateTime.ofInstant(cal.toInstant(), zid);

        return dt.withHour(hour).withMinute(0).withSecond(0).withNano(0);
    }


    public static int getWeek(LocalDateTime date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
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
