import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;

public class Member {

    private String name;
    private final String uuid;
    private double salaryPerHour;
    private int hoursSpent;
    private Map<Activity, Integer> workContribution;

    Member(String name, double salary) throws NameIsEmptyException {
        if (name.isEmpty()) {
            throw new NameIsEmptyException("The field name cannot be empty!");
        }
        this.name = name;
        this.salaryPerHour = salary;
        this.uuid = UUID.randomUUID().toString();
        this.hoursSpent = 0;
        this.workContribution = new TreeMap<>();
    }

    public double calculateSalary() {
        return getTimeSpent() * salaryPerHour;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", SALARY_PER_HOUR=" + salaryPerHour +
                ", timeSpent=" + hoursSpent +
                "Total Salary" + calculateSalary() + '}';
    }

    /**
     * Method for a member to work on an activity. Updates the members contribution and timeSpent on the project.
     *
     * @param activity      Activity to work on
     * @param timeSpent     Time de facto spent working on the activity [hours]
     * @param timeScheduled effectively used time/progress made on the activity
     * @return the cost payed in salary for the work
     */
    public double workOnActivity(Activity activity, int timeSpent, int timeScheduled) {
        hoursSpent += timeSpent;
        if (workContribution.containsKey(activity)) {
            workContribution.put(activity, workContribution.get(activity) + timeScheduled);     // update the contribution according to the effective work
        }
        return timeSpent * salaryPerHour;
    }

    public void spendTime(int timeSpent) {
        hoursSpent += timeSpent;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    //GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public String getID() {
        return uuid;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeSpent() {
        return hoursSpent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(this.uuid, member.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
