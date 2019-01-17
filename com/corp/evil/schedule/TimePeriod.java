import java.time.DayOfWeek;

public class TimePeriod {
    // constants
    private static final int LATER = 1;
    private static final int WORKING_DAYS = 5;

    // member attributes
    private YearWeek start;
    private YearWeek end;

    public TimePeriod(YearWeek start, YearWeek end) {
        if (start.compareTo(end) == LATER) {
            throw new IllegalArgumentException("Start of a TimePeriod can not be later than its end!");
        }

        this.start = start;
        this.end = end;
    }

    public TimePeriod(int startYear, int startWeek, int endYear, int endWeek) {
        this(new YearWeek(startYear, startWeek), new YearWeek(endYear, endWeek));
    }

    /**
     * @return
     */
    public double calculatePassedFraction(YearWeek week, DayOfWeek passedDay) {
        if (week.isBefore(start)) {
            return 0.0;                 // the week is before the relevant time
        }
        if (week.isAfter(end)) {
            return 1.0;
        }

        int totalDays = getDurationInWeeks() * WORKING_DAYS;
        int daysPassed = (new TimePeriod(start, week).getDurationInWeeks() - 1) * WORKING_DAYS;
        daysPassed += passedDay.getValue();

        return ((double) daysPassed) / ((double) totalDays);
    }

    /**
     * Function calculating the duration of a TimePeriod in weeks.
     *
     * @return duration in weeks
     */
    public int getDurationInWeeks() {
        int weeks = end.getWeek() - start.getWeek() + 1;
        int years = end.getYear() - start.getYear();
        return weeks + years * YearWeek.WEEKS_PER_YEAR;
    }

    /**
     * Function to calculate the duration of a TimePeriod if it is possibly null.
     *
     * @param timePeriod TimePeriod of which the duration in weeks shall be determined.
     * @return duration in weeks
     */
    public static int getDurationInWeeksStatically(TimePeriod timePeriod) {
        if (timePeriod == null) {
            return 0;
        }
        return timePeriod.getDurationInWeeks();
    }

    /**
     * Function checking whether a TimePeriod is within another one.
     *
     * @param otherPeriod that should start no earlier than this and end no later than this.
     * @return boolean telling whether otherPeriod is within this
     */
    public boolean isWithin(TimePeriod otherPeriod) {
        if (start.compareTo(otherPeriod.getStart()) == YearWeek.BEFORE) {   // other period starts before start?
            return false;
        }
        return (end.compareTo(otherPeriod.getEnd()) != YearWeek.AFTER);     // end is not after surrounding period's end?
    }

    /**
     * Method comparing the temporal order of this and otherPeriod
     *
     * @param otherPeriod a TimePeriod we want to compare the start of this to.
     * @return -1 if this starts before otherPeriod (or ends before if they start in the same week),
     * 0 if they are identical,
     * 1 if this starts after otherPeriod (or ends after otherPeriod if they started together)
     */
    public int compareTo(TimePeriod otherPeriod) {
        int result = this.start.compareTo(otherPeriod.getStart());
        if (result == 0) {
            result = this.end.compareTo(otherPeriod.getEnd());
        }
        return result;
    }

    public YearWeek getStart() {
        return start;
    }

    public void setStart(YearWeek start) {
        this.start = start;
    }

    public YearWeek getEnd() {
        return end;
    }

    public void setEnd(YearWeek end) {
        this.end = end;
    }
}
