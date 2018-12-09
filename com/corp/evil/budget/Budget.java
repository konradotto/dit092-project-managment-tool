import java.util.List;

public class Budget {

    private final static double DEFAULT_COST_SCHEDULED = 0;
    private final static double DEFAULT_COST_PERFORMED = 0;
    private final static double DEFAULT_PERCENT_COMPLETED = 0;

    private double costOfWorkScheduled;
    private double costOfWorkPerformed;
    private double percentCompleted;

    private double earnedValue;


    public Budget(double costScheduled, double costPerformed, double percentCompleted) {
        this.costOfWorkScheduled = costScheduled;
        this.costOfWorkPerformed = costPerformed;
        this.percentCompleted = percentCompleted;
    }

    public Budget() {
        this(DEFAULT_COST_SCHEDULED, DEFAULT_COST_PERFORMED, DEFAULT_PERCENT_COMPLETED);
    }

    public Budget(List<Budget> budgets) {

    }
}
