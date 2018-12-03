
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum RiskProbability {
    VERY_UNLIKELY(1, "very unlikely"),
    UNLIKELY(2, "unlikely"),
    POSSIBLE(3, "possible"),
    LIKELY(4, "likely"),
    VERY_LIKELY(5, "very likely");

    private final int probability;
    private final String text;

    private final static Map<Integer, RiskProbability> map;

    static {
        HashMap<Integer, RiskProbability> tempMap = new HashMap<>();
        for (RiskProbability prob : RiskProbability.values()) {
            tempMap.put(prob.getProbability(), prob);
        }
        map = Collections.unmodifiableMap(tempMap);
    }

    RiskProbability(int probability, String text) {
        this.probability = probability;
        this.text = text;
    }

    public int getProbability() {
        return probability;
    }

    public String getText() {
        return text;
    }

    public static RiskProbability valueOf(int probability) throws RiskProbabilityNotDefinedException {
        if (!map.containsKey(probability)) {
            throw new RiskProbabilityNotDefinedException("No RiskProbability exists for the suggested probability.");
        }
        return map.get(probability);
    }
}
