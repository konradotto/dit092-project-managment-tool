import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RiskMatrix {
    private ArrayList<Risk> risks;

    public RiskMatrix() {
        risks = new ArrayList<>();
    }

    public RiskMatrix(List<Risk> risks) {
        this();
        addRisks(risks);
    }

    public RiskMatrix(String jsonText) {
        //TODO use gson
    }

    public RiskMatrix(File file) {
        this(RiskMatrix.fromJsonFile(file));
    }

    // TODO: Constructor from JSON-file

    public void addRisk(Risk risk) {
        if (risk == null) {
            throw new IllegalArgumentException("RiskMatrix can not add NULL value to risks");
        }

        this.risks.add(risk);
    }

    public void addRisks(List<Risk> risks) {
        for(Risk risk:risks) {
            addRisk(risk);
        }
    }

    public static String fromJsonFile(File file) {

        // TODO: read json file to String
        String jsonText = "TODO!!!";
        return jsonText;
    }
}
