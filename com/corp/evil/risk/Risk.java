import java.util.HashMap;
import java.util.Objects;

/**
 * @author Konrad Otto
 * @author Marcus Olsson
 */
public class Risk {

    private String riskName;
    private RiskProbability probability;
    private RiskImpact impact;

    private final static String[] riskSeverity = {"minor", "moderate", "major", "severe"};
    private final static int[] riskBorders = {4, 10, 20, Integer.MAX_VALUE};

    public Risk(String riskName, int probability, int impact) throws RiskProbabilityNotDefinedException, RiskImpactNotDefinedException {
        setRiskName(riskName);
        setProbability(probability);
        setImpact(impact);
    }

    /**
     * Getter for the risk using probability and impact
     *
     * @return product of probability and impact (risk)
     */
    public int getRisk() {
        return getProbability() * getImpact();
    }

    public String getRiskString() {
        int risk = getRisk();
        String severity = "";

        int i = 0;
        while (i < riskBorders.length && severity.equals("")) {
            if (risk < riskBorders[i]) {
                severity = riskSeverity[i];
            }
            ++i;
        }
        return severity;
    }

    /*
     *  Auto-generated equals and hashCode (based on riskName)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Risk risk = (Risk) o;
        return Objects.equals(riskName, risk.riskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(riskName);
    }

    /*
     *  Automatically generated getters and setters
     */
    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public int getProbability() {
        return probability.ordinal() + 1;
    }

    public void setProbability(int probability) throws RiskProbabilityNotDefinedException {
        this.probability = RiskProbability.valueOf(probability);
    }

    public int getImpact() {
        return impact.ordinal() + 1;
    }

    public void setImpact(int impact) throws RiskImpactNotDefinedException {
        this.impact = RiskImpact.valueOf(impact);
    }
}