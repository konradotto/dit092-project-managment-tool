import java.util.Objects;

/**
 * @author Konrad Otto
 */
public class Risk {

    private String riskName;
    private int probability;
    private int impact;

    public Risk(String riskName, int probability, int impact) {
        this.riskName = riskName;
        this.probability = probability;
        this.impact = impact;
    }

    public int getRisk() {
        return probability * impact;
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
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getImpact() {
        return impact;
    }

    public void setImpact(int impact) {
        this.impact = impact;
    }
}