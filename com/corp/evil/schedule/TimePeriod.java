public class TimePeriod {

    public static final int EARLIEST_YEAR_ALLOWED = 1900;

    private int startWeek;
    private int startYear;
    private int endWeek;
    private int endYear;

    public TimePeriod(int startWeek, int startYear, int endWeek, int endYear) {
        if (startYear > endYear) {
            throw new IllegalArgumentException("Start year can not be later than end year!" + Print.LS);
        }
        if (startYear < EARLIEST_YEAR_ALLOWED) {
            throw new IllegalArgumentException("This program only allows periods starting from the year "
                    + EARLIEST_YEAR_ALLOWED + "!");
        }

        if (startYear == endYear && startWeek > endWeek) {
            throw new IllegalArgumentException("Start of a TimePeriod can not be later than its end!");
        }

        this.startWeek = startWeek;
        this.startYear = startYear;
        this.endWeek = endWeek;
        this.endYear = endYear;
    }

    public boolean isWithin(TimePeriod otherPeriod) {
        if (otherPeriod.getStartYear() > startYear ||
                (otherPeriod.getStartYear() == startYear && otherPeriod.getStartWeek() > startWeek)) {       // otherPeriod starts after this
            return false;
        }
        return otherPeriod.getEndYear() >= endYear &&
                (otherPeriod.getEndYear() != endYear || otherPeriod.getEndWeek() >= endWeek);
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
