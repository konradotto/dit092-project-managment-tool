import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum RiskImpact {
    VERY_LOW(1, "very low"),
    LOW(2, "low"),
    MEDIUM(3, "medium"),
    HIGH(4, "high"),
    VERY_HIGH(5, "very high");

    private final int impact;
    private final String text;

    private final static Map<Integer, RiskImpact> map;

    static {
        HashMap<Integer, RiskImpact> tempMap = new HashMap<>();
        for (RiskImpact impact : RiskImpact.values()) {
            tempMap.put(impact.getImpact(), impact);
        }
        map = Collections.unmodifiableMap(tempMap);
    }

    RiskImpact(int impact, String text) {
        this.impact = impact;
        this.text = text;
    }

    public int getImpact() {
        return impact;
    }

    public String getText() {
        return text;
    }

    public static RiskImpact valueOf(int impact) throws RiskImpactNotDefinedException {
        if (!map.containsKey(impact)) {
            throw new RiskImpactNotDefinedException("No RiskImpact is defined for the requested value: " + impact +
                    Print.LS + "Please enter a value between " + RiskImpact.VERY_LOW + " and " + RiskImpact.VERY_HIGH);
        }
        return map.get(impact);
    }
}
