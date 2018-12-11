import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;

public class ProjectSchedule {

    private final static int FIRST_WORKDAY = Calendar.MONDAY;
    private final static int LAST_WORKDAY = Calendar.FRIDAY;
    private final static int DAY_START_HOUR = 8;
    private final static int DAY_END_HOUR = 17;
    private final int LAST_WEEK_OF_YEAR = 52;

    private final static int COLUMN_WIDTH = 15;
    private final static int COLUMNS = 5;



    private ArrayList<Activity> activities;
    private LocalDateTime start;
    private LocalDateTime end;

    public ProjectSchedule (ArrayList<Activity> activities) {
        this.activities = activities;
        this.getEndWeek();
        this.getStartWeek();
    }

    public ProjectSchedule(int startYear, int startWeek, int endYear, int endWeek, ArrayList<Activity> activities) {
        this.start = getLocalDateTime(startYear, startWeek, FIRST_WORKDAY, DAY_START_HOUR);
        this.end = getLocalDateTime(endYear, endWeek, LAST_WORKDAY, DAY_END_HOUR);

        this.activities = activities;
    }

    public void addActivity(Activity activity) throws ActivityAlreadyRegisteredException, ActivityIsNullException {
        if (activity == null) {
            throw new ActivityIsNullException("Activity is NULL and cannot be added to the list of activities!");
        }
        if (activities.contains(activity)) {
            throw new ActivityAlreadyRegisteredException("This activity already exists!");
        } else activities.add(activity);
    }

    public void removeActivity(Activity activity) throws ActivityIsNullException {
        if (activity == null) {
            throw new ActivityIsNullException("Project schedule can not remove NULL value from schedule");
        } else {
            activities.remove(activity);
        }
    }

    @java.lang.Override
    public java.lang.String toString() {
        return formatTable();
    }

    private String formatTableRow(String[] columns) {
        String result = "";

        for(int i = 0; i < COLUMNS - 1; ++i) {
            result += String.format("%1$-" + COLUMN_WIDTH + "s", columns[i]);
        }
        result += String.format(columns[3] + "%n");

        return result;
    }

    public String formatTable() {
        StringBuilder sb = new StringBuilder();
        String newline = System.lineSeparator();
        if (activities.isEmpty()) {
            sb.append("There are no activities registered in this team yet." + newline);
        } else {

            sb.append("\t\t\t TASKS " + newline);

            sb.append(String.join("", Collections.nCopies((COLUMNS-1) * COLUMN_WIDTH +1, "-")));
            sb.append(newline);

            // format table content
            sb.append(formatTableRow(new String[] {"| Task name:", "| Start Week:", "| End Week:", "| "}));

            // separator line

            sb.append(String.join("", Collections.nCopies((COLUMNS-1) * COLUMN_WIDTH +1, "-")));
            sb.append(newline);

            for (Activity activity : activities) {
                sb.append(formatTableRow(new String[] {"| " + activity.getName(),
                        "| " + activity.getStartWeek(),
                        "| " + activity.getEndWeek(),
                        "| " }));

                sb.append(String.join("", Collections.nCopies((COLUMNS-1) * COLUMN_WIDTH +1, "-")));
                sb.append(newline);
            }
        }
        //test
        return sb.toString();
    }


    public List<Activity> getParticipation(Member member) {
        List<Activity> result = new ArrayList<Activity>();
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
        return ProjectSchedule.getWeek(this.start);
    }

    public int getEndWeek() {
        return ProjectSchedule.getWeek(this.end);
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
