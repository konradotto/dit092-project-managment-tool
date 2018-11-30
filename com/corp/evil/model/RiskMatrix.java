import java.util.HashSet;
import java.util.Set;

public class RiskMatrix {
    private HashSet<Risk> risks;

    RiskMatrix() {
        risks = new HashSet<>();
    }

    RiskMatrix(Set<Risk> risks) {
        this();
        addRisks(risks);
    }

    // TODO: Constructor from JSON-file

    void addRisk(Risk risk) {
        if (risk == null) {
            throw new IllegalArgumentException("RiskMatrix can not add NULL value to risks");
        }

        this.risks.add(risk);
    }

    void addRisks(Set<Risk> risks) {
        for(Risk risk:risks) {
            addRisk(risk);
        }
    }
}
