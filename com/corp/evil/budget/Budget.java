import java.util.List;

public class Budget {

    private final static String LS = System.lineSeparator();
    private final static String CURRENCY = "$";

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Budget:" + LS);
        sb.append("Cost of work scheduled: \t" + CURRENCY + " " + costOfWorkScheduled + LS);
        sb.append("Cost of work performed: \t" + CURRENCY + " " + costOfWorkPerformed + LS);
        sb.append("Completion: \t\t\t\t" + percentCompleted + " %" + LS);

        return sb.toString();
    }
}
