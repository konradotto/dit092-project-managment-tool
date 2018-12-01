public enum RiskImpact {
    VERY_LOW(1, "very low"),
    LOW(2, "low"),
    MEDIUM(3, "medium"),
    HIGH(4, "high"),
    VERY_HIGH(5, "very high");

    private int value;
    private String word;

    RiskImpact(int value, String word) {
        this.value = value;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public int getValue() {
        return value;
    }
}
