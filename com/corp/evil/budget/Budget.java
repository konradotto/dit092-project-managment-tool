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

    public Budget(double costScheduled, double costPerformed, double percentCompleted) {
        this.costOfWorkScheduled = costScheduled;
        this.costOfWorkPerformed = costPerformed;
        this.percentCompleted = percentCompleted;
    }

    public Budget() {
        this(DEFAULT_COST_SCHEDULED, DEFAULT_COST_PERFORMED, DEFAULT_PERCENT_COMPLETED);
    }


    public Budget(List<Budget> budgets) {
        costOfWorkScheduled = costOfWorkPerformed = percentCompleted = 0.0;

        double totalHours = 0.0;
        int length = budgets.size();
        for (Budget temp : budgets) {
            costOfWorkScheduled += temp.getCostOfWorkScheduled();
            costOfWorkPerformed += temp.getCostOfWorkPerformed();
            percentCompleted += (temp.getPercentCompleted() / length);
        }
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

    public double getEarnedValue() {
        return costOfWorkScheduled * (percentCompleted / 100.0);
    }

    public double getCostOfWorkScheduled() {
        return costOfWorkScheduled;
    }

    public void setCostOfWorkScheduled(double costOfWorkScheduled) {
        this.costOfWorkScheduled = costOfWorkScheduled;
    }

    public double getCostOfWorkPerformed() {
        return costOfWorkPerformed;
    }

    public void setCostOfWorkPerformed(double costOfWorkPerformed) {
        this.costOfWorkPerformed = costOfWorkPerformed;
    }

    public double getPercentCompleted() {
        return percentCompleted;
    }

    public void setPercentCompleted(double percentCompleted) {
        this.percentCompleted = percentCompleted;
    }
}
