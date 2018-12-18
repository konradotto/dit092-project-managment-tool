
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class Member {

    private String name;
    private final String uuid;
    private double SALARY_PER_HOUR;
    private Duration timeSpent;

    Member(String name, double salary)throws NameIsEmptyException{
        if (name.isEmpty()){ throw new NameIsEmptyException("The field name cannot be empty!"); }
        this.name = name;
        SALARY_PER_HOUR = salary;
        uuid = UUID.randomUUID().toString();
        timeSpent = Duration.ofHours(0);


    }

    public double calculateSalary(){
        return getTimeSpent()*getSALARY_PER_HOUR();
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", SALARY_PER_HOUR=" + SALARY_PER_HOUR +
                ", timeSpent=" + timeSpent +
               "Total Salary" + calculateSalary() +'}';
    }

    public void spendTime(long hours){
        setTimeSpent(timeSpent.plusHours(hours));
    }

    public void setSALARY_PER_HOUR(double SALARY_PER_HOUR) {
        this.SALARY_PER_HOUR = SALARY_PER_HOUR;
    }

    //GETTERS AND SETTERS
    public String getName() {return name;}
    public String getID() {return uuid;}
    public double getSALARY_PER_HOUR() {return SALARY_PER_HOUR;}
    public void setName(String name) {this.name = name;}
    public void setTimeSpent(Duration timeSpent) {this.timeSpent = timeSpent;}
    public long getTimeSpent() {return timeSpent.toHours();}

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
