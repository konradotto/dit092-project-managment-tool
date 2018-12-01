public enum RiskImpact {
    INSIGNIFICANT (1, "insignificant"),
    MINOR (2, "minor"),
    MODERATE (3, "moderate"),
    MAJOR (4, "major"),
    CRITICAL (5, "critical");

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
