import java.util.UUID;

/**
 * @author Konrad Otto
 */
public class Risk {

    private UUID uuid;
    private String riskName;
    private int probability;
    private int impact;

    public Risk(String riskName, int probability, int impact) {
        uuid = UUID.randomUUID();
        this.riskName = riskName;
        this.probability = probability;
        this.impact = impact;
    }


    public int getRisk() {
        return probability * impact;
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
