public class YearWeek implements Comparable<YearWeek> {

    static final int BEFORE = -1;
    static final int AFTER = 1;

    static final int EARLIEST_YEAR_ALLOWED = 1900;
    static final int LATEST_YEAR_ALLOWED = 2100;
    static final int FIRST_WEEK = 1;
    static final int WEEKS_PER_YEAR = 52;

    private int year;
    private int week;

    public YearWeek(int year, int week) {
        if (year < EARLIEST_YEAR_ALLOWED) {
            throw new IllegalArgumentException("This program only allows periods starting from the year "
                    + EARLIEST_YEAR_ALLOWED + "!");
        }
        if (year > LATEST_YEAR_ALLOWED) {
            throw new IllegalArgumentException("This program only allows periods ending no later than the year "
                    + LATEST_YEAR_ALLOWED + "!");
        }

        if (week < FIRST_WEEK) {
            throw new IllegalArgumentException("YearWeek was < " + FIRST_WEEK + "! " +
                    "Weeks need to be integers in the interval " + FIRST_WEEK + " - " + WEEKS_PER_YEAR + "!");
        }
        if (week > WEEKS_PER_YEAR) {
            throw new IllegalArgumentException("YearWeek was > " + WEEKS_PER_YEAR + "! " +
                    "Weeks need to be integers in the interval " + FIRST_WEEK + " - " + WEEKS_PER_YEAR + "!");
        }

        this.year = year;
        this.week = week;
    }

    public String toString() {
        return String.format("%d, %d", week, year);
    }

    @Override
    public int compareTo(final YearWeek other) {
        int result = Integer.compare(this.getYear(), other.getYear());      // compare the years first
        if (result == 0) {
            result = Integer.compare(this.getWeek(), other.getWeek());      // compare the weeks if years are indecisive
        }
        return result;
    }

    public boolean isWithin(TimePeriod timePeriod) {
        if (this.isBefore(timePeriod.getStart())) {
            return false;
        }

        return (!this.isAfter(timePeriod.getEnd()));
    }

    public boolean isBefore(final YearWeek other) {
        return compareTo(other) == BEFORE;
    }

    public boolean isAfter(final YearWeek other) {
        return compareTo(other) == AFTER;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
