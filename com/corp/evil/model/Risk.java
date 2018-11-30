import java.util.Objects;

/**
 * @author Konrad Otto
 */
public class Risk {

    private String riskName;
    private int probability;
    private int impact;

    Risk(String riskName, int probability, int impact) {
        this.riskName = riskName;
        this.probability = probability;
        this.impact = impact;
    }

    int getRisk() {
        return probability * impact;
    }

    @Override
    public String toString() {
        return "Risk{" +
                "riskName='" + riskName + '\'' +
                ", probability=" + probability +
                ", impact=" + impact +
                '}';
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
    String getRiskName() {
        return riskName;
    }

    void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    int getProbability() {
        return probability;
    }

    void setProbability(int probability) {
        this.probability = probability;
    }

    int getImpact() {
        return impact;
    }

    void setImpact(int impact) {
        this.impact = impact;
    }
}
