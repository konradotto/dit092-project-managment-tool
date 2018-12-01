import java.util.*;
import java.time.Duration;


public class Member {

    private String name;
    private final UUID ID = null;
    private final double SALARY_PER_HOUR;
    private Duration timeSpent;

    Member(String name, double salary){
        this.name = name;
        this.SALARY_PER_HOUR = salary;
    }

    public double calculateSalary(){

        return 0;
    }

    public void spendTime(Duration time){
    }

    //GETTERS AND SETTERS
    public String getName() {return name;}
    public UUID getID() {return ID;}
    public double getSALARY_PER_HOUR() {return SALARY_PER_HOUR;}
    public void setName(String name) {this.name = name;}
    public void setTimeSpent(Duration timeSpent) {this.timeSpent = timeSpent;}
    public Duration getTimeSpent() {return timeSpent;}
}
