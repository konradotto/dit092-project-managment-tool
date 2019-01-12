import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class YearWeekTest {

    public static class NonParameterizedYearWeekTests {

        @Test(expected = IllegalArgumentException.class)
        public void testConstructWithYearTooSmall() {
            new YearWeek(YearWeek.EARLIEST_YEAR_ALLOWED - 1, YearWeek.WEEKS_PER_YEAR);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testConstructWithYearTooBig() {
            new YearWeek(YearWeek.LATEST_YEAR_ALLOWED + 1, YearWeek.WEEKS_PER_YEAR);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testConstructWithWeekTooSmall() {
            new YearWeek(YearWeek.EARLIEST_YEAR_ALLOWED, YearWeek.FIRST_WEEK - 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testConstructWithWeekTooBig() {
            new YearWeek(YearWeek.EARLIEST_YEAR_ALLOWED, YearWeek.WEEKS_PER_YEAR + 1);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParameterizedYearWeekTests {

        // fields used as parameters must be public
        @Parameterized.Parameter(0)
        public int year1;
        @Parameterized.Parameter(1)
        public int week1;
        @Parameterized.Parameter(2)
        public int year2;
        @Parameterized.Parameter(3)
        public int week2;
        @Parameterized.Parameter(4)
        public int result1;
        @Parameterized.Parameter(5)
        public int result2;

        // creating the test data
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            Object[][] data = new Object[][]{
                    {2010, 12, 2010, 13, -1, 1},
                    {2010, 42, 2010, 42, 0, 0},
                    {2010, 15, 2011, 15, -1, 1},
                    {1950, 1, 1949, 52, 1, -1}
            };
            return Arrays.asList(data);
        }

        @Test
        public void testCompareTo() {
            YearWeek time1 = new YearWeek(year1, week1);
            YearWeek time2 = new YearWeek(year2, week2);
            assertEquals("Week " + week1 + " " + year1 + " compareTo " + week2 + " " + year2 + ".",
                    result1, time1.compareTo(time2));
            assertEquals("Week " + week2 + " " + year2 + " compareTo " + week1 + " " + year1 + ".",
                    result2, time2.compareTo(time1));
        }
    }
}
