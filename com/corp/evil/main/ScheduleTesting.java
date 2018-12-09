import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ScheduleTesting {

    public static void main(String[] args) {
        ProjectSchedule schedule = new ProjectSchedule(2018, 49, 2018, 49, new ArrayList<Activity>());
        System.out.println(schedule);

        System.out.println(schedule.getStartWeek());
        System.out.println(schedule.getEndWeek());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        System.out.println(formatter.format(schedule.getStart()));
        System.out.println(formatter.format(schedule.getEnd()));
    }
}
