public class TimePeriod {

    private static final int LATER = 1;

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
     * Function checking whether a TimePeriod is within another one.
     *
     * @param otherPeriod that should start no earlier than this and end no later than this.
     * @return boolean telling whether otherPeriod is within this
     */
    public boolean isWithin(TimePeriod otherPeriod) {
        if (start.compareTo(otherPeriod.getStart()) == LATER) {         // other period starts before start?
            return false;
        }
        return (otherPeriod.getEnd().compareTo(end) != LATER);          // other period doesn't end after end?
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
