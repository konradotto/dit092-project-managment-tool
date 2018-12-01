import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum RiskImpact {
    VERY_LOW(1, "very low"),
    LOW(2, "low"),
    MEDIUM(3, "medium"),
    HIGH(4, "high"),
    VERY_HIGH(5, "very high");

    private final static Map<Integer, RiskImpact> map;

    static {
        HashMap<Integer, RiskImpact> tempMap = new HashMap<Integer, RiskImpact>();
        for (RiskImpact impact : RiskImpact.values()) {
            tempMap.put(impact.getValue(), impact);
        }
        map = Collections.unmodifiableMap(tempMap);
    }

    private final int value;
    private final String text;

    private RiskImpact(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static RiskImpact valueOf(int value) throws RiskImpactNotDefinedException {
        if (!map.containsKey(value)) {
            throw new RiskImpactNotDefinedException("The provided integer can not be parsed into a " +
                    "valid RiskImpact object");
        }
        return map.get(value);
    }
}
