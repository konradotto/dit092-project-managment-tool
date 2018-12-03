
import java.time.Duration;
import java.util.UUID;

public class Member {

    private String name;
    private final String uuid;
    private final double SALARY_PER_HOUR;
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

    public void spendTime(long hours){
        Duration spendTime = timeSpent.plusHours(hours);
        setTimeSpent(spendTime);
    }

    //GETTERS AND SETTERS
    public String getName() {return name;}
    public String getID() {return uuid;}
    public double getSALARY_PER_HOUR() {return SALARY_PER_HOUR;}
    public void setName(String name) {this.name = name;}
    public void setTimeSpent(Duration timeSpent) {this.timeSpent = timeSpent;}
    public long getTimeSpent() {return timeSpent.toHours();}
}
