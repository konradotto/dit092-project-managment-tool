public enum RiskProbability {
    VERY_UNLIKELY(1, "very unlikely"),
    UNLIKELY(2, "unlikely"),
    POSSIBLE(3, "possible"),
    LIKELY(4, "likely"),
    VERY_LIKELY(5, "very likely");

    private int value;
    private String word;

    RiskProbability(int value, String word) {
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
